package com.tiankong.service;

import com.tiankong.entity.User;
import com.tiankong.utils.CommonResult;
import com.tiankong.vo.LoginForm;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    CommonResult<User> login(LoginForm loginForm, HttpServletResponse response);
}
