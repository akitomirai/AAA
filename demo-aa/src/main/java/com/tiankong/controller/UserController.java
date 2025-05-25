package com.tiankong.controller;

import com.tiankong.entity.User;
import com.tiankong.service.UserService;
import com.tiankong.utils.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return userService.login(loginForm, response);
    }
}
