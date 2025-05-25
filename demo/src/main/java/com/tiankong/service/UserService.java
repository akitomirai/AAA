package com.tiankong.service;

import com.tiankong.pojo.LoginUser;
import com.tiankong.pojo.User;

public interface UserService {
    User login(LoginUser loginUser);

    Long add(User user);

    void resetPassword(String userId);
}
