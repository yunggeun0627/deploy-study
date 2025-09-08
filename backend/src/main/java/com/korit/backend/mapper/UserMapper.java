package com.korit.backend.mapper;

import com.korit.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User user);
    User findByUsername(String username);
    User findById(Integer id);
}
