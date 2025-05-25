package com.tiankong.mapper;

import com.tiankong.pojo.Directory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DirectoryMapper {

    // 查询根节点（level=0）
    @Select("SELECT id FROM directory WHERE clevel = 0 LIMIT 1")
    Integer findRootId();
    // 递归查询子树
    List<Directory> findSubTree(@Param("id") Integer id);

    // 添加知识点
    void addDirectory(Directory directory);

    // 根据ID 获取知识点
    Directory getById(Integer parentId);

    // 修改知识点
    void updateChildIds(Directory parent);

    // 递归统计待删除的节点总数（包含自身）
    int countSubtreeNodes(Integer id);

    // 执行删除操作（触发CASCADE）
    int deleteById(Integer id);
}