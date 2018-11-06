package io.github.isliqian.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.isliqian.utils.HttpRequest;
import io.github.isliqian.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("wx")
public class WeChatController {

    private String appId = "wx890cca1140a7f166";
    private String secret = "5a56f4dd6cc32df70b491a37e07662c6";

    @GetMapping("/callback")
    public ResultUtil list(HttpServletRequest request, HttpServletResponse response){

        String code = request.getParameter("code");
        String api = "https://api.weixin.qq.com/sns/jscode2session" ;
//        Map<String,String> map = new HashMap<>();
//        map.put("appid",appId);
//        map.put("secret",secret);
//        map.put("js_code",code);
//        map.put("grant_type","authorization_code");
        try{
            //转换为json格式字符串
            String result = HttpRequest.sendGet(api,"appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");
            JSONObject json = JSONObject.parseObject(result);
            return ResultUtil.success(json);
        }catch (Exception e){
            return ResultUtil.internalServerError();
        }

    }
}
