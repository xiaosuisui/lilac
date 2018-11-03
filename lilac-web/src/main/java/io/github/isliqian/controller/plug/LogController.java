package io.github.isliqian.controller.plug;

import io.github.isliqian.core.BaseController;
import io.github.isliqian.log.bean.SysLog;
import io.github.isliqian.log.service.SysLogService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
@RequestMapping("/log/log")
public class LogController extends BaseController {

//        @Autowired
//        private ParametersService parametersService;
@Resource
private SysLogService sysLogService;
    @GetMapping("console")
    @ApiOperation("实时日志查看")
    public String console(){
        return "/log/console.html";
    }
    @GetMapping("/list")
    @ApiOperation(value="日志列表")
    public ModelAndView list(SysLog sysLog, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        List<SysLog> logList = sysLogService.findList( sysLog);
        mav.setViewName("/log/log.html");
        mav.addObject("list",logList);
        return mav;
    }



        /**
         * 日志列表
         *
         * @param
         * @param model
         * @return
         */
        @RequestMapping("/files")
        public String logsFiles( Model model) {

            File logdirs = new File(System.getProperty("user.dir")+"\\logs\\");

            List<Map<String, String>> dirs = new ArrayList<>();
            common(logdirs,dirs);
            model.addAttribute("dirs", dirs);
            return "/log/logfiles.html";
        }


        @RequestMapping("download")
        public String downloadLog(HttpServletRequest request, HttpServletResponse response,Model model) {
            // 读取tomcat目录下logs/
            String path = request.getParameter("path");
            if (StringUtils.isBlank(path)) {
                path = File.separator;
            }
            // path 中不能有../
            File file = new File(path);
            try {
                if (file.isDirectory()){
                    List<Map<String, String>> dirs = new ArrayList<>();
                    common(file,dirs);
                    model.addAttribute("dirs", dirs);
                    return "/log/logfiles.html";
                }
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

        public void common(File logdirs,List<Map<String, String>> dirs){
           /* File parent = new File(logdirs.getPath());
            if (parent.isDirectory()){
                Map<String, String> lastDir = new HashMap<>();
                lastDir.put("name",parent.getName());
                lastDir.put("path",parent.getPath());
                lastDir.put("type", "");
                lastDir.put("method","返回");
                lastDir.put("dir", parent.isDirectory() + "");
                dirs.add(lastDir);
            }*/

            // 文件夹在上面 TODO 排序不好
            for (File file : logdirs.listFiles()) {
                Map<String, String> dirOrFile = new HashMap<>();
                if (file.isDirectory()){
                    dirOrFile.put("name", file.getName());
                    dirOrFile.put("path", URLEncoder.encode(file.getPath()));
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
                    dirOrFile.put("path", URLEncoder.encode(file.getPath()));
                    dirOrFile.put("type", "文件");
                    dirOrFile.put("method","下载");
                    dirOrFile.put("dir", file.isDirectory() + "");
                    dirs.add(dirOrFile);
                }

            }
        }

}
