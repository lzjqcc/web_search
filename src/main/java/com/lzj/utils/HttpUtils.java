package com.lzj.utils;

import com.lzj.domain.WebEntity;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.sync.methods.HttpGet;


import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.hc.core5.ssl.SSLContexts.createDefault;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class HttpUtils {
    private final static List<WebEntity> list=new ArrayList<WebEntity>();
    private final static String URL="http://news.baidu.com/n?cmd=4&class=sportnews&pn=1&from=tab&qq-pf-to=pcqq.group";

    public static List<WebEntity> listWebEntities() {
       Connection connection= Jsoup.connect(URL);

        try {
            Document document=connection.get();
            return parseHTML(document.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static List<WebEntity> parseHTML(String content){
        Pattern pattern=Pattern.compile("<a[^>]+>[^<]*</a>");
        Matcher matcher=pattern.matcher(content);
        System.out.println("begin");
        while (matcher.find()){
            try {
                WebEntity entity=new WebEntity(getUUID(),matcher.group());
                list.add(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
