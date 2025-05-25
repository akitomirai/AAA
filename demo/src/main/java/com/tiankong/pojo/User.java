package com.tiankong.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;                    // 用户ID（建议改用Long防止大数字溢出）
    private String userName;            // 用户姓名
    private String userAccount;         // 用户账号
    private String userAvatar;          // 用户头像
    private Integer gender;             // 性别（0-未知 1-男 2-女，建议改用枚举类型）
    private String userRole;            // 用户角色（0-普通用户 1-管理员，建议改用枚举类型）
    private String userPassword;        // 用户密码（加密存储，建议增加@JsonIgnore注解）
    private Long organizeId;            // 组织ID（建议根据关联表类型改为Long）
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
    private Integer isDelete;           // 是否删除（建议改为boolean类型，默认false）

}