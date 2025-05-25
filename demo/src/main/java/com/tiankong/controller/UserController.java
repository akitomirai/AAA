package com.tiankong.controller;

import com.tiankong.pojo.LoginUser;
import com.tiankong.utils.Result;
import com.tiankong.pojo.User;
import com.tiankong.service.imp.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /* 登录 */
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser,HttpSession session){

        // 计算 MD5（使用 Apache Commons Codec）
        String md5Hash = DigestUtils.md5Hex(loginUser.getUserAccount() + loginUser.getUserPassword());

        loginUser.setUserPassword(md5Hash);
        log.info("登录：{}",loginUser);
        User user = userService.login(loginUser);
        if(user != null ){
            // 生成唯一 Token
            String token = UUID.randomUUID().toString();
            session.setAttribute("token", token);
            session.setAttribute("currentUser", user);
            log.info("用户 {} 登录成功，Token 已生成", user.getUserName());
            return Result.success(user);
        }
        return Result.error("用户名或密码错误");
    }

    /* 添加用户 */
    @PostMapping("/add")
    public Result user(@RequestBody User user){
        String md5Hash = DigestUtils.md5Hex(user.getUserAccount() + user.getUserPassword());
        user.setUserPassword(md5Hash);
        log.info("添加user: {}",user);
        try {
            Long id = userService.add(user);
            return Result.success(id);
        } catch (Exception e) {
            return Result.error("用户已存在");
        }
    }

    /* 管理员重置密码 */
    @GetMapping("/resetPassword")
    public Result resetPassword(String userId){
        log.info("重置密码:{}",userId);
        userService.resetPassword(userId);
        return Result.success("true");
    }
}
