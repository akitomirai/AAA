package com.tiankong.service.imp;

import com.tiankong.mapper.DirectoryMapper;
import com.tiankong.pojo.Directory;
import com.tiankong.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public List<Directory> getDirectoryTree(Integer id) {
        // 处理未传入ID的情况：查找根节点
        if (id == null) {
            id = directoryMapper.findRootId();
            if (id == null) {
                throw new RuntimeException("根目录不存在");
            }
        }
        // 递归查询所有子节点
        List<Directory> allNodes = directoryMapper.findSubTree(id);
        // 构建树形结构
        return buildTree(allNodes);
    }

    // 将扁平列表转换为树形结构
    private List<Directory> buildTree(List<Directory> nodes) {
        Map<Integer, Directory> nodeMap = new HashMap<>();
        List<Directory> roots = new ArrayList<>();
        // 初始化节点映射
        for (Directory node : nodes) {
            node.setChildren(new ArrayList<>());
            nodeMap.put(node.getId(), node);
        }
        // 建立父子关系
        for (Directory node : nodes) {
            Integer parentId = node.getParentId();
            if (parentId != null && nodeMap.containsKey(parentId)) {
                Directory parent = nodeMap.get(parentId);
                parent.getChildren().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

    @Override
    @Transactional
    public void addDirectory(Directory directory) {
        Integer parentId = directory.getParentId();
        Directory parent = new Directory();
        // 1. 处理根目录
        if (parentId == null) {
            directory.setClevel(0);
        } else {
            parent = directoryMapper.getById(parentId);
            if (parent == null) throw new IllegalArgumentException("父目录不存在");
            directory.setClevel(parent.getClevel() + 1);
        }
        // 2. 插入并强制刷新事务
        directoryMapper.addDirectory(directory);

        if (parentId != null) {
            String newChildIds = (parent.getChildId() == null)
                    ? String.valueOf(directory.getId())
                    : parent.getChildId() + "," + directory.getId();
            parent.setChildId(newChildIds.replaceAll("null,?", ""));
            directoryMapper.updateChildIds(parent);  // 更新父目录
        }
    }

    @Override
    @Transactional
    public Integer deleteById(Integer id) {
        // 1. 统计将被删除的节点总数（包含自身）
        int count = directoryMapper.countSubtreeNodes(id);

        Integer parentId = directoryMapper.getById(id).getParentId();

        if (parentId != null) {
            Directory parent = directoryMapper.getById(parentId);
            if (parent != null && parent.getChildId() != null) {
                // 从child_id中移除被删除的ID及其子节点ID（自动处理）
                String newChildIds = Arrays.stream(parent.getChildId().split(","))
                        .filter(childId -> !childId.equals(String.valueOf(id)))
                        .collect(Collectors.joining(","));
                parent.setChildId(newChildIds);
                directoryMapper.updateChildIds(parent);
            }
        }

        // 2. 执行删除（触发级联删除）
        directoryMapper.deleteById(id);

        return count;
    }

    @Override
    public void updateChildIds(Directory directory) {
        directoryMapper.updateChildIds(directory);
    }
}