package io.github.isliqian.utils;

import java.io.*;

/**
 * 访问量，注册量
 */
public class CountUtils {


    /*
     * 获取txt文件中的数字，即之前的访问量
     * 传入参数为： 字符串: txtFilePath,文件的绝对路径
     */
    public static Long count(String txtFilePath) {

        try {
            //读取文件(字符流)
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePath),"UTF-8"));
            //循环读取数据
            String str = null;
            StringBuffer content = new StringBuffer();
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            //关闭流
            in.close();
            // 解析获取的数据
            Long count = Long.valueOf(content.toString());
            count ++; // 访问量加1
            //写入相应的文件
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFilePath),"UTF-8"));
            out.write(String.valueOf(count));

            //清楚缓存
            out.flush();
            //关闭流
            out.close();

            return count;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0L;
        }


    }
}
