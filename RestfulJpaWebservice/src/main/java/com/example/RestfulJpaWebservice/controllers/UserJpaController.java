package com.example.RestfulJpaWebservice.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.example.RestfulJpaWebservice.beans.Post;
import com.example.RestfulJpaWebservice.beans.User;
import com.example.RestfulJpaWebservice.exception.UserNotFoundException;
import com.example.RestfulJpaWebservice.repository.PostJpaRepository;
import com.example.RestfulJpaWebservice.repository.UserJpaRepository;
import com.example.RestfulJpaWebservice.repository.UserRepositoryImpl;

@RestController
public class UserJpaController {
	
	
	
	@Autowired
	
	private UserJpaRepository userRepository;
	
     @Autowired
	
	private PostJpaRepository postRepository;
	
	//// Get /users
	//retriveAllUsers
	
	@GetMapping("jpa/users")
	
	public List<User> retrieveAllUsers()
	{
		return userRepository.findAll();
	}
	
	// Get users/{id}
	//retriveOneUser
		
	@GetMapping("jpa/users/{id}")
	
	public Resource<User> retrieveOneUser(@PathVariable int id)
	{
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id "+ id+" is not found");
		}
		
		Resource<User> resource=new Resource(user.get());
		
		ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	//Create new user
	//Users/user
		//To add new user
	
	@PostMapping("jpa/users")
	
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user)
	{
		User savedUser=userRepository.save(user);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	//to delete one user
	@DeleteMapping("jpa/users/{id}")
	
	public void deleteById(@PathVariable int id)
	{
		userRepository.deleteById(id);
		
		
	}
	
	
	//To get list of posts of one user
	@GetMapping("/jpa/users/{id}/posts")
	
	public List<Post> retrieveAllPost(@PathVariable int id)
	{
		Optional<User> optionalUser=userRepository.findById(id);
		
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("id "+ id+" is not found");
		
		return optionalUser.get().getPosts();
	}

	//To add new post to the user
	@PostMapping("/jpa/users/{id}/posts")
	
	public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Post post)
	{
		Optional<User> optionaluser=userRepository.findById(id);
		
		if(!optionaluser.isPresent())
		{
			throw new UserNotFoundException("id "+ id+" is not found");
		}
		User user=optionaluser.get();
	    post.setUser(user);
	    postRepository.save(post);
	    
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(post.getPid()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
