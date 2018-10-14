package io.github.isliqian.controller;

import io.github.isliqian.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游客角色可以访问的页面
 */
@RestController
@RequestMapping("/api/v1/guest")
public class GuestController {

    @GetMapping("/welcome")
    public ResultUtil login() {
        return ResultUtil.success("欢迎访问游客页面！");
    }
}
