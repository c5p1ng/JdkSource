package com.c5p1ng.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.c5p1ng.mybatis.mapper.UserMapper;
import com.c5p1ng.mybatis.pojo.User;

public class Start {
	public static void main(String[] args) {
		String resource = "config/mybatis-config.xml";
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = userMapper.getUser("111");
			System.out.println(user.getAccount());
		} finally {
			session.close();
		}
	}
}
