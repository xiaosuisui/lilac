package io.github.isliqian.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 视图控制错误码
 */
@Controller
public class ViewErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == 404) {
            return "/error/404";
        }
        if (statusCode == 500){
            return "/error/500";
        }
//        if (statusCode == 403) {
//            return "/error/403";
//        }
//        if (statusCode == 503) {
//            return "/error/503";
//        }
//        if (statusCode == 500) {
//            return "/error/500";
//        }
        return null;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
