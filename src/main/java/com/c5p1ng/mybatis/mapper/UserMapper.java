package com.c5p1ng.mybatis.mapper;

import java.util.List;

import com.c5p1ng.mybatis.pojo.User;

public interface UserMapper {
	public User getUser(String userId);
	public List<User> getList();
}
