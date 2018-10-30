package io.github.isliqian.controller;

import io.github.isliqian.sys.bean.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
@Api(value = "路由控制管理")
public class IndexController {


    @GetMapping("/")
    @ApiOperation(value="首页")
    public String index(){

      return "index.html";
    }
    @GetMapping("/signin")
    @ApiOperation(value="登陆")
    public String signin(Model model){
        model.addAttribute("user",new SysUser());
        return "/user/login.html";
    }
    @GetMapping("/password")
    @ApiOperation(value="找回密码")
    public ModelAndView password(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/user/forget.html");
        return mav;
    }
    @GetMapping("/home/console.html")
    @ApiOperation(value="控制面板")
    public String home(Model model){
        // 获取访问量信息
        String basePath = System.getProperty("user.dir");
        String txtFilePath = basePath+"\\count.txt";
        Long count = Get_Visit_Count(txtFilePath);
        model.addAttribute("count", count); // 后台参数传递给前端
        return "/home/console.html";
    }
    @GetMapping("/set/system/website")
    @ApiOperation(value="网站设置")
    public ModelAndView website(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/set/system/website.html");
        return mav;
    }

    @GetMapping("/set/system/email")
    @ApiOperation(value="邮件服务")
    public ModelAndView email(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/set/system/email.html");
        return mav;
    }



    /*
     * 获取txt文件中的数字，即之前的访问量
     * 传入参数为： 字符串: txtFilePath,文件的绝对路径
     */
    public static Long Get_Visit_Count(String txtFilePath) {

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

            //System.out.println(content);
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
