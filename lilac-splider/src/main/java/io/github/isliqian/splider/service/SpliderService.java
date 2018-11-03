package io.github.isliqian.splider.service;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.splider.bean.BasicCollege;
import io.github.isliqian.splider.util.SpliderUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxt.liqian
 * @version 2018/11/3
 * @desc
 */
@Service
public class SpliderService {

    @Resource
    private RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(SpliderService.class);

    /**
     * @param pageNum
     * @return java.lang.String
     * @author wxt.liqian
     * @version 2018/8/3
     * URL规律
     */
    public  String url(int pageNum){
        String url = "https://gaokao.chsi.com.cn/sch/search--ss-on,option-qg,searchType-1,start-"+pageNum+".dhtml";
        return url;
    }
    public  int getMaxPageNum()  {
        int pageMaxNum=0;
        //获取请求信息
        Document doc = SpliderUtils.getDocument(url(0));
        //获取页面总数
        Elements elements =doc.select("li.lip");
        pageMaxNum = Integer.parseInt(elements.get(7).text());
        return pageMaxNum;
    }

    @Async
    public  List<BasicCollege> spliderCollegeInfo() {
        List<BasicCollege> basicCollegeList = new ArrayList<>();
        try {
            int pageMaxNum = getMaxPageNum();

            for (int j=0;j<pageMaxNum;j++){
                logger.info("开始爬取爬取高校基本信息,url:" +url(j*20));
                Connection conn= Jsoup.connect(url(j*20));//获取请求连接
                //获取请求信息
                Document doc = null;
                doc = conn.get();

                //获取高校基本信息
                Elements elements = doc.select("table.ch-table").select("tr");
                for (int i = 1;i < elements.size(); i++) {
                    BasicCollege basicCollege =new BasicCollege();
                    //获取每一行的列
                    Elements tds = elements.get(i).select("td");
                    basicCollege.setName(tds.get(0).text());
                    basicCollege.setGaokaoUrl(SpliderUtils.gaokao+tds.get(0).select("a").attr("href"));
                    basicCollege.setLocation(tds.get(1).text());
                    basicCollege.setSubject(tds.get(2).text());
                    basicCollege.setType(tds.get(3).text());
                    basicCollege.setLevel(tds.get(4).text());
                    basicCollege.setFeature(tds.get(5).text());
                    if ("\uE664".equals(tds.get(6).text())){
                        basicCollege.setPostgraduate("1");
                    }else {
                        basicCollege.setPostgraduate("0");
                    }

                    basicCollege.setSatisfaction(tds.get(7).text());
                    basicCollege.setBaikeUrl("https://baike.baidu.com/item/"+ basicCollege.getName());
                    basicCollegeList.add(basicCollege);
                }
                if (j>pageMaxNum)break;
            }
            logger.info("爬取高校基本信息结束,一共爬取到"+basicCollegeList.size()+"条数据......");
            redisService.remove("college");
        } catch (Exception e) {
            redisService.remove("college");
            logger.info("爬取高校信息出现异常",e.toString());
        }
        return basicCollegeList;
    }


}
