package com.example.RestfulJpaWebservice.repository;

import java.util.List;

import com.example.RestfulJpaWebservice.beans.User;

public interface UserRepository {

	public List<User> findAll();
	
	public User findOne(int id);
	
	public User save(User user);
	
	public User delete(int id);
}
