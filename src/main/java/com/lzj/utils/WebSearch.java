package com.lzj.utils;

import com.lzj.domain.WebEntity;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import java.io.IOException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/**

 * Created by li on 17-6-3.
 */
public class WebSearch {
    private Analyzer analyzer;
    private final String INDEX_DIR="/home/li/GitRepotitoy/web_search/src/main/webapp/index_dir";
    public WebSearch(){
        analyzer=new StandardAnalyzer();
    }
    public List<WebEntity> listWebEntities(String key){
        List<WebEntity> entities= null;
        try {
            entities = new ArrayList<WebEntity>();
            Path path= FileSystems.getDefault().getPath(INDEX_DIR);
            FSDirectory directory=FSDirectory.open(path);
            DirectoryReader reader=DirectoryReader.open(directory);
            IndexSearcher searcher=new IndexSearcher(reader);
            MultiFieldQueryParser parser=new MultiFieldQueryParser(new String[]{"url"},analyzer);
            Query query=parser.parse(key);
            TopDocs topDocs=searcher.search(query,200);
           ScoreDoc[]scoreDocs= topDocs.scoreDocs;
           for (ScoreDoc scoreDoc:scoreDocs){
              int docID= scoreDoc.doc;
              Document document=searcher.doc(docID);
              WebEntity entity=new WebEntity();
              entity.setUrl(getHightLight(document.get("url"),query,"url"));
              entities.add(entity);
           }
        } catch (ParseException  e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
        }
        return  entities;
    }
    private String getHightLight(String text,Query query,String filedName){
        org.apache.lucene.search.highlight.Scorer scorer=new QueryScorer(query);
        Formatter formatter=new SimpleHTMLFormatter("<font color='red'>","</font>");
        Highlighter highlighter = new Highlighter(formatter,scorer);
        try {
            highlighter.setTextFragmenter(new SimpleFragmenter(200));
            String lightText= highlighter.getBestFragment(analyzer,filedName,text);
            if (lightText!=null){
                return lightText;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }
        return text;
    }

}
