package io.github.isliqian.controller;

import io.github.isliqian.bean.Console;
import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.core.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@Api(value = "路由控制管理")
public class IndexController extends BaseController {

    @Resource
    private RedisService redisService;

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/")
    @ApiOperation(value="首页")
    public String index(Model model){
      SysUser user = (SysUser) redisService.get("user");
      if (user==null){
          return "redirect:/signin";
      }else {
          addMessage(model,"欢迎使用lilac后台管理系统 V1.0.0");
          model.addAttribute("user",user);
      }
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

        Console console = (Console) redisService.get("console");
        if (console == null){
            console = new Console();
            console.setVisitCount(Long.valueOf(1));
            console.setUserCount(Long.valueOf(sysUserService.findAll().size()));
            redisService.set("console",console);
        }else {
            console.setVisitCount(1+console.getVisitCount());
            redisService.set("console",console);
        }
        model.addAttribute("console", console);         // 后台参数传递给前端
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

    @GetMapping("/job/index")
    @ApiOperation(value = "定时任务")
    public String job(Model model){
        return "/job/JobManager.html";
    }


    @RequestMapping("/.well-known/acme-challenge/*")
    public ResponseEntity<String> check(HttpServletRequest request, HttpServletResponse response){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        String result="";
        try {
            String URI=request.getRequestURI().replace("/","\\");
            //文件路径自行替换一下就行,就是上图中生成验证文件的路径,因为URI中已经包含了/.well-known/acme-challenge/,所以这里不需要
            File file=new File( System.getProperty("user.dir")+URI);
            InputStream is = new FileInputStream(file);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(("验证文件").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        }catch (Exception e){

        }
        return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
    }

    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
    }
}
