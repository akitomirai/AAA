package com.tiankong.pojo.dto;

import com.tiankong.pojo.Directory;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class DirectoryDTO {
    private Integer id;
    private String name;
    private Integer clevel;
    private List<DirectoryDTO> children;

    // 转换方法：从 Directory 到 DirectoryDTO
    public static DirectoryDTO convert(Directory directory) {
        if (directory == null) {
            return null; // 或抛出 IllegalArgumentException("Directory不能为null")
        }

        DirectoryDTO dto = new DirectoryDTO();
        dto.setId(directory.getId());
        dto.setName(directory.getName());
        dto.setClevel(directory.getClevel());

        // 处理子节点（确保children不为null）
        List<Directory> children = directory.getChildren();
        if (children == null) {
            dto.setChildren(Collections.emptyList()); // 显式设置空列表
        } else {
            // 递归转换 + 过滤空值（防御性编程）
            dto.setChildren(children.stream()
                    .map(DirectoryDTO::convert)
                    .filter(Objects::nonNull) // 过滤转换失败的子节点
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static List<DirectoryDTO> convertList(List<Directory> directories) {
        if (directories == null || directories.isEmpty()) {
            return Collections.emptyList();
        }
        return directories.stream()
                .map(DirectoryDTO::convert)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}