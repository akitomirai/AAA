package com.tiankong.mapeer;

import com.tiankong.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByUserAccount(String userAccount);
}
