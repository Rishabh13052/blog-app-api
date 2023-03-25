package com.java.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
