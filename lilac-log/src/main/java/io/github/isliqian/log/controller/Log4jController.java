package io.github.isliqian.log.controller;

import io.github.isliqian.utils.Encodes;
import io.github.isliqian.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/log4j")
@Slf4j
public class Log4jController  {

//        @Autowired
//        private ParametersService parametersService;


        @RequestMapping("level")
        public String level(HttpServletRequest request) {


            return "modules/log/log4j";
        }


        /**
         * 日志列表
         *
         * @param request
         * @param model
         * @return
         */
        @RequestMapping("logs")
        public String logsFiles(HttpServletRequest request, HttpServletResponse response, Model model) {

            File logdirs = new File(System.getProperty("user.dir")+"\\logs\\");

            List<Map<String, String>> dirs = new ArrayList<>();
            for (File file : logdirs.listFiles()) {
                Map<String, String> dirOrFile = new HashMap<>();
                dirOrFile.put("name", file.getName());
                dirOrFile.put("path", URLEncoder.encode(System.getProperty("user.dir")+"\\logs+\\"));// 相对于 logs目录
                dirOrFile.put("dir", file.isDirectory() + "");
                dirs.add(dirOrFile);
            }
            model.addAttribute("dirs", dirs);
            return "/plug/logfiles.html";
        }


        @RequestMapping("download")
        public String downloadLog(HttpServletRequest request, HttpServletResponse response) {
            // 读取tomcat目录下logs/
            String path = URLDecoder.decode(request.getParameter("path"));
            if (StringUtils.isBlank(path)) {
                path = File.separator;
            }
            // path 中不能有../
            path = path.replace(".." + File.separator, "");
            String relativePath = "";
//                    parametersService.getStringByKey("logs.relative.tomcat.path");
            //  basePath === tomcat/logs/
            String basePath = System.getProperty("catalina.home")+relativePath.replace("##",File.separator);
            if(log.isDebugEnabled()){
                log.debug("tomcat home:"+basePath);
                log.debug("file path:"+basePath + path);
            }

            File file = new File(basePath + path);
            try {
                response.setHeader("Content-Disposition", " attachment; filename=" + Encodes.urlEncode(file.getName()));
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
                log.error("下载文件失败!", e);
            }
            return null;
        }

        public static void main(String[] args){
            File logdirs = new File(System.getProperty("user.dir")+"\\logs\\");
            for (File file : logdirs.listFiles()) {
                System.out.println(file.getName());
            }

        }
}
