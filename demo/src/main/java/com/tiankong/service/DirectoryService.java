package com.tiankong.service;

import com.tiankong.pojo.Directory;
import java.util.List;

public interface DirectoryService{

    List<Directory> getDirectoryTree(Integer id);

    void addDirectory(Directory directory);

    Integer deleteById(Integer id);

    void updateChildIds(Directory directory);
}