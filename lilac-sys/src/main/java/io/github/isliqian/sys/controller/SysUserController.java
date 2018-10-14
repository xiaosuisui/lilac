package io.github.isliqian.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SysUserController {

    @GetMapping("/signin")
    public String signin(){

        System.out.println("-----");
        return "signin";
    }
}
