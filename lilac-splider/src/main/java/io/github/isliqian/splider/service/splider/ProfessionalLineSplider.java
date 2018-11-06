package io.github.isliqian.splider.service.splider;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.splider.bean.BasicCollege;
import io.github.isliqian.splider.bean.ProfessionalLine;
import io.github.isliqian.splider.service.CollegeService;
import io.github.isliqian.splider.service.ProfessionalLineService;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wxt.liqian
 * @version 2018/11/5
 * @desc 爬取各专业录取分数线
 */
@Component
public class ProfessionalLineSplider {


    @Resource
    private RedisService redisService;

    @Resource
    private CollegeService collegeService;


    @Resource
    private ProfessionalLineService professionalLineService;

    private static final Logger logger = LoggerFactory.getLogger(ProfessionalLineSplider.class);



    /**
     * @return void
     * @author wxt.liqian
     * @version 2018/8/11
     * 百度百科各专业录取分数线
     */
    public synchronized void start() {
        List<BasicCollege> list = collegeService.findList(new BasicCollege());
        List<ProfessionalLine> professionalLines = new ArrayList<>();
        //错误链接存储
        Set<String> errorLinks = new HashSet<>();
        ProfessionalLine professionalLine = null;

        List<WebElement> sectionElements = null;
        WebElement sectionElementBtn = null;

        WebElement yearElementBtn = null;
        List<WebElement> yearElements = null;

        WebElement provinceElementBtn = null;
        List<WebElement> provinceElements = null;


        loop1:for (int i = 0; i < list.size(); i++) {
            WebDriver driver = SpliderUtils.getDriver();
            WebDriverWait wait = SpliderUtils.getWait(10);
            Actions actions = SpliderUtils.getActions();
            SpliderUtils.openChorme();
            driver.get(list.get(i).getBaikeUrl());
            logger.info("开始爬取爬取高校历年分数线,url:" + list.get(i).getBaikeUrl());
            try {
                // 点击历年分数线
                driver.findElement(By.xpath("//*[@id=\"pageTabs\"]/a[2]/i/span")).click();
                logger.info("开始爬取爬取高校各专业录取分数线,url:" + list.get(i).getBaikeUrl());

                //根据理科文科
                sectionElements = driver.findElements(By.xpath("//dt[@id='majorScoresFilter']//div[@id='majorFilter_course']//a"));
                sectionElementBtn = driver.findElement(By.xpath("//*[@id=\"totalScoresFilter\"]/div[1]/a"));
            } catch (Exception e) {
                logger.error("没有该分数线或xpath路径错误" + e.toString());
                errorLinks.add(list.get(i).getBaikeUrl());
                driver.quit();
                continue loop1;//跳出本次循环
            }

            WebElement collegeElement = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[1]/div[1]/dl/dd[1]/h1"));
//        String college = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", collegeElement);


            for (WebElement sectionElement : sectionElements) {
                try {
                    actions.moveToElement(sectionElementBtn).build().perform();
                    sectionElement.click();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.error("找不到该科目" + e.toString());
                    errorLinks.add(list.get(i).getBaikeUrl());
                }
                String section = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", sectionElement);

                //根据省份
                yearElementBtn = driver.findElement(By.xpath("//dt[@id='majorScoresFilter']//div[@class='filterBox'][2]/a"));
                yearElements = driver.findElements(By.xpath("//dt[@id='majorScoresFilter']//div[@id='majorFilter_year']//a[@class='filterItem']"));
                for (WebElement yearElement : yearElements) {
                    actions.moveToElement(yearElementBtn).build().perform();
                    yearElement.click();
                    String year = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", yearElement);


                    provinceElementBtn = driver.findElement(By.xpath("//dt[@id='majorScoresFilter']//div[@class='filterBox'][3]//a[@class='filterBtn']"));
                    provinceElements = driver.findElements(By.xpath("//dt[@id='majorScoresFilter']//div[@id='majorFilter_province']//a[@class='filterItem']"));


                    //System.out.println(provinceElements.size());
                    for (WebElement provinceElement : provinceElements) {

                        try {
                            actions.moveToElement(provinceElementBtn).build().perform();
                            provinceElement.click();
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            logger.error("找不到该省份" + e.toString());
                            errorLinks.add(list.get(i).getBaikeUrl());
                        }
                        Document doc = Jsoup.parse(driver.getPageSource());
                        Elements trElements = doc.getElementById("majorScoresTable").select("tbody").select("tr");
                        for (Element tr : trElements) {
                            Elements tdElements = tr.getElementsByTag("td");
//                        System.out.println(tdElements.html());
                            professionalLine = new ProfessionalLine();
                            if ("暂无数据".equals(tdElements.html())) {

                                professionalLine.setCollege(list.get(i));
                                professionalLine.setSection(section);
                                professionalLine.setYear(year);
                                professionalLine.setProfession(null);
                                professionalLine.setAverage(null);
                                professionalLine.setHighest(null);
                                professionalLine.setBatch(null);
                                professionalLines.add(professionalLine);
                            } else {

                                professionalLine.setCollege(list.get(i));
                                professionalLine.setSection(section);
                                professionalLine.setYear(year);
                                professionalLine.setProfession(tdElements.get(0).text());
                                professionalLine.setAverage(tdElements.get(1).text());
                                professionalLine.setHighest(tdElements.get(2).text());
                                professionalLine.setBatch(tdElements.get(3).text());
                                professionalLines.add(professionalLine);
                            }

                            professionalLineService.save(professionalLine);
                            logger.info(professionalLine.toString());
                        }
                    }
                }


            }
            logger.info("爬取高校各专业录取分数线结束,一共爬取到" + professionalLines.size() + "条数据......");
            driver.quit();
            redisService.remove("professionalLine");
            //TODO 执行批量存储操作
        }
    }
}
