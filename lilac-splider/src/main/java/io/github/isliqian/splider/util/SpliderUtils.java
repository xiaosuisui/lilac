package io.github.isliqian.splider.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * @author wxt.liqian
 * @version 2018/11/3
 * @desc 爬虫工具类
 */
public class SpliderUtils {

    public static final String gaokao="https://gaokao.chsi.com.cn";



    private static WebDriver driver = null ;

    private static WebDriverWait wait =null;

    private static Actions actions = null;

    private static Connection conn=null;

    private static  Document doc = null;
    /**
     * @author liqian
     * @Description:
     * @date 18-9-1 上午8:14
     * 获取Jsoup Document 对象
     */
    public static Document getDocument(String url){

        try {
            conn= Jsoup.connect(url);//获取请求连接
            //获取请求信息
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    /**
     * @return org.openqa.selenium.WebDriver
     * @author wxt.liqian
     * @version 2018/8/11
     * 获取WebDriver
     */
    public static WebDriver getDriver(){
        driver = new ChromeDriver();
        return driver;
    }

    /**
     *
     * @param time
     * @return
     * 获取DriverWait,并设置时间
     */
    public static WebDriverWait getWait(int time){
        wait = new WebDriverWait(driver, time);
        return wait;

    }

    /**
     * @author liqian
     * @Description:
     * @date 18-9-1 上午8:15
     * 获取Action对象
     */
    public static Actions getActions(){
        actions = new Actions(driver);
        return actions;
    }

    /**
     * @author liqian
     * @Description:
     * @date 18-9-1 上午8:15
     * 打开Chrome浏览器
     */
    public static void openChorme(String driverName,String driverDir){
        System.setProperty(driverName, driverDir);
    }


}
