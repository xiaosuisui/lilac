package io.github.isliqian.shiro.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.github.isliqian.shiro.utils.JsonMapper;
import io.github.isliqian.shiro.utils.UserAgentUtils;
import org.springframework.stereotype.Service;

@Service
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {
    public UserFilter() {
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (UserAgentUtils.isAjaxRequest((HttpServletRequest)request)) {
            this.saveRequest(request);
            Map<String, Object> map = new HashMap();
            map.put("code", "-1");
            map.put("message", "未登陆");
            String string = JsonMapper.toJsonString(map);

            try {
                response.reset();
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().print(string);
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        } else {
            this.saveRequestAndRedirectToLogin(request, response);
        }

        return false;
    }
}
