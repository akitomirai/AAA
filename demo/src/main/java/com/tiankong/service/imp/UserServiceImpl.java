package com.tiankong.service.imp;

import com.tiankong.mapper.UserMapper;
import com.tiankong.pojo.LoginUser;
import com.tiankong.pojo.User;
import com.tiankong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(LoginUser loginUser) {
        return userMapper.login(loginUser);
    }

    @Override
    public Long add(User user) {
        userMapper.add(user);
        return user.getId();
    }

    @Override
    public void resetPassword(String userId) {
        userMapper.resetPassword(userId);
    }
}
