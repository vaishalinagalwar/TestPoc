package com.example.RestfulJpaWebservice.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.RestfulJpaWebservice.beans.User;
@Component
public class UserRepositoryImpl implements UserRepository {

	private static List<User> users=new ArrayList<User>();
	
	private static int countId=3;
	static
	{
		users.add(new User(1,"Nilesh",new Date()));
		users.add(new User(2,"Vijay",new Date()));
		users.add(new User(3,"Ganavee",new Date()));
	}
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public User findOne(int id) {
		// TODO Auto-generated method stub
		for(User user:users)
		{
			if(user.getId()==id)
				return user;
		}
		return null;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		if(user.getId()==null)
		{
			user.setId(++countId);
			
		}
		users.add(user);
		return user;
	}

	@Override
	public User delete(int id) {
		// TODO Auto-generated method stub
		
		Iterator<User> itr=users.iterator();
		while(itr.hasNext())
		{
			User user=itr.next();
			if(user.getId()==id)
			{
				itr.remove();
				
			}
			return user;
		}
		return null;
	}

}
