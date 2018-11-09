package io.github.isliqian.splider.service.splider;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.splider.bean.College;
import io.github.isliqian.splider.bean.HistoricalLine;
import io.github.isliqian.splider.service.CollegeService;
import io.github.isliqian.splider.service.HistoricalLineService;
import io.github.isliqian.splider.util.SpliderUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wxt.liqian
 * @version 2018/11/5
 * @desc 爬取高校历年分数线
 */
@Component
public class HistoricalLineSplider {

    @Resource
    private RedisService redisService;

    @Resource
    private HistoricalLineService historicalLineService;

    @Resource
    private CollegeService collegeService;

    private static final Logger logger = LoggerFactory.getLogger(HistoricalLineSplider.class);



    /**
     * @author wxt.liqian
     * @version 2018/11/5
     * @desc synchronized保证此方法
     */
    @Async
    public synchronized void start() {
        List<College> list = collegeService.findList(new College());

        List<HistoricalLine> historicalLines = new ArrayList<>();
        //错误链接存储
        Set<String> errorLinks = new HashSet<>();
        HistoricalLine historicalLine = null;
        WebElement sectionElementBtn = null;
        List<WebElement> sectionElements =null;

        loop1:for (int i =0;i<list.size();i++){
            WebDriver driver = SpliderUtils.getDriver();
            WebDriverWait wait = SpliderUtils.getWait(10);
            Actions actions = SpliderUtils.getActions();
            SpliderUtils.openChorme();
            try{
                driver.get(list.get(i).getBaikeUrl());
            }catch (TimeoutException e){
                logger.error("超时错误"+e.toString());
                continue loop1;
            }
            logger.info("开始爬取爬取高校历年分数线,url:"+list.get(i).getBaikeUrl() );


           try{
               // 点击历年分数线
               driver.findElement(By.xpath("//*[@id=\"pageTabs\"]/a[2]/i/span")).click();
               //根据理科文科
               sectionElementBtn = driver.findElement(By.xpath("//*[@id=\"totalScoresFilter\"]/div[1]/a"));
               sectionElements = driver.findElements(By.xpath("//dt[@id='totalScoresFilter']//div[@id='totalFilter_course']/a"));
           }catch (Exception e){
               logger.error("没有该分数线或xpath路径错误"+e.toString());
               errorLinks.add(list.get(i).getBaikeUrl());
               driver.quit();
               continue loop1;//跳出本次循环
           }


            //根据省份
            WebElement provinceElementBtn = driver.findElement(By.xpath("//*[@id=\"totalScoresFilter\"]/div[2]/a"));
            List<WebElement> provinceElements = driver.findElements(By.xpath("//div[@id='totalFilter_province']//a[@class='filterItem']"));
//            WebElement collegeElement = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[1]/div[1]/dl/dd[1]/h1"));
//            String college = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", collegeElement);
            for (WebElement sectionElement : sectionElements) {
                try {
                    actions.moveToElement(sectionElementBtn).build().perform();
                    sectionElement.click();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.error("找不到该科目"+e.toString());

                    redisService.remove("historicalLine");
                    errorLinks.add(list.get(i).getBaikeUrl());
                }


                String section = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", sectionElement);
                //System.out.println(section);
                for (WebElement provinceElement : provinceElements) {
                    try {
                        actions.moveToElement(provinceElementBtn).build().perform();
                        provinceElement.click();
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        logger.error("爬取高校历年分数线出现异常2"+e.toString());
                        redisService.remove("historicalLine");
                        errorLinks.add(list.get(i).getBaikeUrl());
                    }
                    String city = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", provinceElement);
                    //System.out.println(city);

                    Document doc = Jsoup.parse(driver.getPageSource());
                    Elements trElements = doc.getElementById("totalScoresTable").select("tbody").select("tr");
                    for (Element tr : trElements) {
                        historicalLine = new HistoricalLine();
                        historicalLine.setCollege(list.get(i));
                        historicalLine.setCity(city);

                        Elements tdElements = tr.getElementsByTag("td");

                        historicalLine.setSection(section);
                        historicalLine.setYear(tdElements.get(0).text());
                        historicalLine.setHighest(tdElements.get(1).text());
                        historicalLine.setAverage(tdElements.get(2).text());
                        historicalLine.setLowest(tdElements.get(3).text());
                        historicalLine.setBatch(tdElements.get(4).text());
                        historicalLines.add(historicalLine);
                        historicalLineService.save(historicalLine);
                        logger.info(historicalLine.toString());
                    }


                }
            }


            driver.quit();
        }
        logger.info("爬取高校历年分数线结束,一共爬取到"+historicalLines.size()+"条数据......");
        redisService.remove("historicalLine");
        redisService.set("errorLinks",errorLinks);
        //TODO 执行批量存储操作
    }
}
