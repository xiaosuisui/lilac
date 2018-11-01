package io.github.isliqian.log.controller;

import io.github.isliqian.utils.StringUtils;
import io.github.isliqian.utils.base.BaseController;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plug/log4j")
public class Log4jController  extends BaseController {

//        @Autowired
//        private ParametersService parametersService;


        @RequestMapping("/level")
        public String level(HttpServletRequest request,Model model) {
            addMessage(model,"暂只支持日志文件下载");
            String logName = request.getParameter("log");
            if (null != logName) {
                Logger log = ("".equals(logName) ?
                        Logger.getRootLogger() : Logger.getLogger(logName));
                log.setLevel(Level.toLevel(request.getParameter("level"), Level.DEBUG));
            }
            Logger rootLogger =  Logger.getRootLogger() ;
            model.addAttribute("rootLogger",rootLogger);
            return "/plug/log4j.html";
        }


        /**
         * 日志列表
         *
         * @param request
         * @param model
         * @return
         */
        @RequestMapping("/")
        public String logsFiles( Model model) {

            File logdirs = new File(System.getProperty("user.dir")+"\\logs\\");

            List<Map<String, String>> dirs = new ArrayList<>();
            // 文件夹在上面 TODO 排序不好
            for (File file : logdirs.listFiles()) {
                Map<String, String> dirOrFile = new HashMap<>();
                if (file.isDirectory()){
                    dirOrFile.put("name", file.getName());
                    dirOrFile.put("path", URLEncoder.encode(System.getProperty("user.dir")+"\\logs\\"));// 相对于 logs目录
                    dirOrFile.put("type", "文件夹");
                    dirOrFile.put("method","打开");
                    dirOrFile.put("dir", file.isDirectory() + "");
                    dirs.add(dirOrFile);
                }

            }
            for (File file : logdirs.listFiles()) {
                Map<String, String> dirOrFile = new HashMap<>();
                if (file.isFile()){
                    dirOrFile.put("name", file.getName());
                    dirOrFile.put("path", URLEncoder.encode(System.getProperty("user.dir")+"\\logs\\"));// 相对于 logs目录
                    dirOrFile.put("type", "文件");
                    dirOrFile.put("method","下载");
                    dirOrFile.put("dir", file.isDirectory() + "");
                    dirs.add(dirOrFile);
                }

            }
            model.addAttribute("dirs", dirs);
            return "/plug/logfiles.html";
        }


        @RequestMapping("download")
        public String downloadLog(HttpServletRequest request, HttpServletResponse response) {
            // 读取tomcat目录下logs/
            String path = request.getParameter("path");
            String name = request.getParameter("name");
            System.out.println(path);
            System.out.println(name);
            if (StringUtils.isBlank(path)) {
                path = File.separator;
            }
            // path 中不能有../
            File file = new File(path+name);
            try {
                response.setHeader("Content-Disposition", " attachment; filename=" + file.getName());
                response.setHeader("Content-Type", "text/plain");
                //response.setHeader("Content-Length",sysFile.getSize()+"");
                // 保存文件

                // 获取输入流
                InputStream is = new FileInputStream(file);
                // 获取输出流 CommonsMultipartFile 中可以直接得到文件的流
                OutputStream os = response.getOutputStream();
                IOUtils.copy(is, os);
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
                return null;
            } catch (Exception e) {
                logger.error("下载文件失败!", e);
            }
            return null;
        }

        public static void main(String[] args){
//            File logdirs = new File(System.getProperty("user.dir")+"\\logs\\");
//            for (File file : logdirs.listFiles()) {
//                System.out.println(file.getName());
//            }
            String relativePath = "";
System.out.println(System.getProperty("catalina.home")+relativePath.replace("##",File.separator));
        }
}
