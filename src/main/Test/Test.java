import com.lzj.domain.WebEntity;
import com.lzj.utils.HttpUtils;
import com.lzj.utils.IndexManager;
import com.lzj.utils.WebSearch;

import java.util.List;

/**
 * Created by li on 17-6-3.
 */
public class Test {
    @org.junit.Test
    public void testHttpUtils(){
        HttpUtils.listWebEntities();
    }
    @org.junit.Test
    public void testIndexManager(){
        IndexManager manager=new IndexManager();
        manager.createIndex();;
    }
    @org.junit.Test
    public void testWebSearch(){
        WebSearch search=new WebSearch();
        List<WebEntity>list=search.listWebEntities("中");
        for (WebEntity entity:list){
            System.out.println(entity.getUrl());
        }
    }
}
