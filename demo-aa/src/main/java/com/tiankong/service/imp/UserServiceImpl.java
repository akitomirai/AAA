package com.tiankong.service.imp;

import com.tiankong.entity.User;
import com.tiankong.mapeer.UserMapper;
import com.tiankong.service.UserService;
import com.tiankong.utils.CommonResult;
import com.tiankong.utils.JwtUtils;
import com.tiankong.utils.MD5Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private JwtUtils jwtUtils;

    @Override
    public CommonResult<User> login(LoginForm loginForm, HttpServletResponse response) {
        // 1. 查询用户
        User user = userMapper.selectByUserAccount(loginForm.getUserAccount());
        if (user == null || user.getIsDelete() == 1) {
            return CommonResult.error(1001, "用户不存在");
        }

        // 2. 验证密码（MD5加密验证）
        String encryptedPwd = MD5Utils.encrypt(loginForm.getUserPassword());
        if (!encryptedPwd.equals(user.getUserPassword())) {
            return CommonResult.error(1002, "密码错误");
        }

        // 3. 生成JWT令牌
        String token = jwtUtils.generateToken(user);
        
        // 4. 设置Cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(3600);  // 1小时有效期
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 5. 脱敏处理
        user.setUserPassword(null);
        return CommonResult.success(user);
    }
}
