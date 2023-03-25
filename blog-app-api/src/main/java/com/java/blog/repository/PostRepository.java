package com.java.blog.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	Page<Post> findByUser(User user, Pageable p);
	Page<Post> findByCategory(Category category, Pageable p);
	
	List<Post> findByTitleContaining(String title);

}
