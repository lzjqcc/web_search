package com.lzj.utils;

import com.lzj.domain.WebEntity;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class IndexManager {
    private StandardAnalyzer analyzer;
    private final String INDEX_DIR="/home/li/GitRepotitoy/web_search/src/main/webapp/index_dir";
    public IndexManager( ){
        analyzer=new StandardAnalyzer();
    }
    public void createIndex(){
        File file=new File(INDEX_DIR);
        IndexWriter writer=null;
        if (!file.exists()){
            try {
                Path path= FileSystems.getDefault().getPath(INDEX_DIR);
                FSDirectory directory= FSDirectory.open(path);

                IndexWriterConfig config=new IndexWriterConfig(analyzer);
                 writer=new IndexWriter(directory,config);
                List<WebEntity>list=HttpUtils.listWebEntities();
                for (WebEntity entity:list){
                    Document document=new Document();
                    document.add(new StringField("id",entity.getId(), Field.Store.YES));
                    document.add(new TextField("url",entity.getUrl(), Field.Store.YES));
                    writer.addDocument(document);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
