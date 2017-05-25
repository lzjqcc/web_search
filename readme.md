# 站内全文搜索

## 使用到的技术

Lucene+DWR+HttpComponents+bootstrap

整体思路：

​	1，利用**HttpComponents抓取百度新闻源码**。

​	2，利用**Java正则表达式匹配超链接**，将超链接保存在实体类WebEntity中。

​	3，接着使用**Lucene创建索引**将WebEntity中的超链接写入Web服务器保存起来。

​	4，当在页面进行搜索时将**含有关键字的内容提取出来**，**关键字**用**红色高亮**显示。

​	5，bootstrap是一种UI库使用它可以很方便的写错不错的界面。

​	6，DWR可以在JavaScript中调用Java代码，在本项目中使用它来检验用户名是否唯一。

最后成果如下图所示。

​	

​		**搜索页面**	![	QQ拼音截图20170525171129](/lzjqcc/web_search/blob/master/129.png)

​		**登录页面**

​		![QQ拼音截图20170525171625](/lzjqcc/web_search/blob/master/625.png)

​		**注册页面**

​		![QQ拼音截图20170525171806](/lzjqcc/web_search/blob/master/806.png)

http://news.baidu.com/n?cmd=4&class=sportnews&pn=1&from=tab&qq-pf-to=pcqq.group

上面是百度新闻的超链接

## HttpComponents

​	

#### 	HttpComponents之抓取网页

​		

```java
/**
     * 抓取网页内容
     * @param path 网页地址
     * @return WebEntity集合
     */
    public static List<WebEntity> catchWebContent(String path){
        List<WebEntity> webEntities=new ArrayList<WebEntity>();
        CloseableHttpClient httpClient=HttpClients.createDefault();
        //封装网址
        HttpGet httpGet=new HttpGet(path);
        CloseableHttpResponse response=null;
        try {
            //访问网站--->就是访问网址为path的网站
            response=httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200){
              //content就是网页内容
               String content= EntityUtils.toString(response.getEntity(),"gb2312");
              // 使用正则表达式将href放入WebEntity中
              //href的形式如：
              //<a href="http://finance.baidu.com/">财经</a>
      				
               regexContent(content,webEntities);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (response!=null)
                     response.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return  webEntities;
    }
```

## Lucene

####       Lucene之创建索引

​		

```java
public class IndexManager {
  	//词法分析器
    private Analyzer analyzer;
    public IndexManager() {
      	//创建词法分析器
        analyzer = new StandardAnalyzer();
    }

    /**
     * 创建索引
     *
     * @param url       指定网页的地址（即百度新闻网址）
     * @param index_dir 指定索引存放的路径
     * @return url
     */
    public String createIndex(String url, String index_dir) {
        try {
            Path path = FileSystems.getDefault().getPath(index_dir);
            Directory directory = FSDirectory.open(path);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter writer = new IndexWriter(directory, config);
          	//catchWebContent使用HttpComponents抓取页面保存到WebEntity集合
            List<WebEntity> webEntities = HttpUtils.catchWebContent(url);
            for (WebEntity entity : webEntities) {
                Document document = new Document();
                document.add(new StringField("id", entity.getId(), Field.Store.YES));
                document.add(new TextField("url", entity.getUrl(), Field.Store.YES));
              //创建索引
                writer.addDocument(document);
            }
            writer.flush();
            writer.commit();
            writer.close();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }
}
```

#### 	Lucene之检索关键字

#### 	

```java
 /**
     * 根据关键字在索引中获取查询结果
     *
     * @param key 关键字
     * @param p   索引路径
     * @return 带有高亮显示的查找结果
     */
    public List<WebEntity> listWebEntities(String key, String p) {
        List<WebEntity> entities = new ArrayList<WebEntity>();
        Path path = FileSystems.getDefault().getPath(p);
        String[] filed = new String[]{"url"};
        QueryParser parser = new MultiFieldQueryParser(filed, analyzer);
        IndexReader reader = null;
        try {
            Directory directory = FSDirectory.open(path);
            reader = DirectoryReader.open(directory);
            Query query = parser.parse(key);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(query, 300);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docID = scoreDoc.doc;
                Document document = searcher.doc(docID);
                WebEntity entity = new WebEntity();
              	//highLight返回的是红色高亮
                entity.setUrl(highLight(document.get("url"),"url",query));
                entity.setId(highLight(document.get("id"),"id",query));
                entities.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return entities;
    }
```

#### 	Lucenne之高亮显示

```java
/**
     * 
     * @param text
     * @param fieldName 创建索引时传入的fieldName
     * @param query
     * @return
     */
    private String highLight(String text,String fieldName,Query query){
        try {
            Highlighter lighter=new Highlighter(new SimpleHTMLFormatter("<font color=\'red\'>", "</font>"), new QueryScorer(query));
            lighter.setTextFragmenter(new SimpleFragmenter(200));//设置经过被高亮处理的文本  字数为两百
            //获得处理之后的文本
            String beatFragment= lighter.getBestFragment(analyzer, fieldName, text);// fieldName 高亮显示的哪个域  text高亮显示出现的文本
            if(beatFragment==null){
                return text;
            }else{
                return beatFragment;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
```

​	

## DWR

​	利用DWR实现JavaScript调用Java代码

