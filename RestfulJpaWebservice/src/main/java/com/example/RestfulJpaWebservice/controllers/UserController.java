package com.example.RestfulJpaWebservice.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.RestfulJpaWebservice.beans.User;
import com.example.RestfulJpaWebservice.exception.UserNotFoundException;
import com.example.RestfulJpaWebservice.repository.UserRepositoryImpl;

@RestController
public class UserController {
	
	@Autowired
	
	private UserRepositoryImpl service;
	@GetMapping("/users")
	
	public List<User> retrieveAllUsers()
	{
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	
	public Resource<User> retrieveOneUser(@PathVariable int id)
	{
		User user=service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id "+ id+" is not found");
		}
		
		Resource<User> resource=new Resource(user);
		
		ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/users")
	
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user)
	{
		User savedUser=service.save(user);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	
	public void deleteById(@PathVariable int id)
	{
		User user=service.delete(id);
		
		if(user==null) {
			throw new UserNotFoundException("id "+ id+" is not found");
		}
	}

}
