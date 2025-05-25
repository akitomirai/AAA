package com.tiankong.mapper;

import com.tiankong.pojo.LoginUser;
import com.tiankong.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /* 登录验证 */
    User login(LoginUser loginUser);

    /* 添加用户 */
    void add(User user);

    /* 重置密码 */
    void resetPassword(String userId);
}
