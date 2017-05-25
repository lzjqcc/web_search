package com.lzj.utils;

import com.lzj.domain.WebEntity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**根据关键字查找url中内容
 * Created by Administrator on 2017/5/25 0025.
 */
public class IndexSearch {
    private Analyzer analyzer;
    public IndexSearch(){
        analyzer=new StandardAnalyzer();
    }
    public List<WebEntity> listWebEntities(){
        List<WebEntity> entities=new ArrayList<WebEntity>();
        
        return  entities;
    }
}
