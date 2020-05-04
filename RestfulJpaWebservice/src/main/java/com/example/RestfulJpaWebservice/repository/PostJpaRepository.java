package com.example.RestfulJpaWebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RestfulJpaWebservice.beans.Post;
import com.example.RestfulJpaWebservice.beans.User;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer>{

}
