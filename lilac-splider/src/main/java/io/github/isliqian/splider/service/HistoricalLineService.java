package io.github.isliqian.splider.service;

import io.github.isliqian.splider.bean.BasicCollege;
import io.github.isliqian.splider.util.SpliderUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxt.liqian
 * @version 2018/11/5
 * @desc 爬取高校历年分数线
 */
@Component
public class HistoricalLineService {


    private static final Logger logger = LoggerFactory.getLogger(HistoricalLineService.class);



    /**
     * @author wxt.liqian
     * @version 2018/11/5
     * @desc synchronized保证此方法
     */
    public synchronized void start(String url) {
        SpliderUtils.openChorme();
        WebDriver driver = SpliderUtils.getDriver();
        WebDriverWait wait = SpliderUtils.getWait(10);
        Actions actions = SpliderUtils.getActions();
        driver.get(url);
        logger.info("开始爬取爬取高校历年分数线,url:"+url );
        // 点击历年分数线
        driver.findElement(By.xpath("//*[@id=\"pageTabs\"]/a[2]")).click();
        List<io.github.isliqian.splider.bean.HistoricalLine> historicalLines = new ArrayList<>();
        io.github.isliqian.splider.bean.HistoricalLine historicalLine = null;
        //根据理科文科
        WebElement sectionElementBtn = driver.findElement(By.xpath("//*[@id=\"totalScoresFilter\"]/div[1]/a"));
        List<WebElement> sectionElements = driver.findElements(By.xpath("//dt[@id='totalScoresFilter']//div[@id='totalFilter_course']/a"));

        //根据省份
        WebElement provinceElementBtn = driver.findElement(By.xpath("//*[@id=\"totalScoresFilter\"]/div[2]/a"));
        List<WebElement> provinceElements = driver.findElements(By.xpath("//div[@id='totalFilter_province']//a[@class='filterItem']"));
        WebElement collegeElement = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[1]/div[1]/dl/dd[1]/h1"));
        String college = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", collegeElement);
        for (WebElement sectionElement : sectionElements) {
            try {
                actions.moveToElement(sectionElementBtn).build().perform();
                sectionElement.click();
                Thread.sleep(3000);
            } catch (Exception e) {
                logger.error("爬取高校历年分数线出现异常1"+e.toString());
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
                }
//                    String city = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", provinceElement);
                //System.out.println(city);

                Document doc = Jsoup.parse(driver.getPageSource());
                Elements trElements = doc.getElementById("totalScoresTable").select("tbody").select("tr");
                for (Element tr : trElements) {
                    historicalLine = new io.github.isliqian.splider.bean.HistoricalLine();

                    Elements tdElements = tr.getElementsByTag("td");
                    historicalLine.setCollege(new BasicCollege());
                    historicalLine.setSection(section);
                    historicalLine.setYear(tdElements.get(0).text());
                    historicalLine.setHighest(tdElements.get(1).text());
                    historicalLine.setAverage(tdElements.get(2).text());
                    historicalLine.setLowest(tdElements.get(3).text());
                    historicalLine.setBatch(tdElements.get(4).text());
                    historicalLines.add(historicalLine);
                    logger.info(historicalLine.toString());
                }


            }
        }
        logger.info("爬取高校历年分数线结束,一共爬取到"+historicalLines.size()+"条数据......");
        driver.quit();
        //TODO 执行批量存储操作
    }
}
