package io.github.isliqian.controller;

import io.github.isliqian.exception.AesException;
import io.github.isliqian.util.WXPublicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@RestController
public class WechatController {



    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);


    @GetMapping("/wxpublic/verify_wx_token")
    public void verifyWXToken(HttpServletRequest request, HttpServletResponse response) throws AesException {
        PrintWriter print;
        // 微信加密签名
        String signature = request.getParameter("signature");          // 时间戳
        String timestamp = request.getParameter("timestamp");          // 随机数
        String nonce = request.getParameter("nonce");            // 随机字符串
        String echostr = request.getParameter("echostr");            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && WXPublicUtils.checkSignature(signature, timestamp, nonce)) {
            try {
                print = response.getWriter();                                        print.write(echostr);
                print.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
