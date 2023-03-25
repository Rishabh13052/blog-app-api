package com.java.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.blog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
