package com.java.blog.service;

import java.util.List;

import com.java.blog.payload.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	public void deleteCategory(Integer categoryId);
	
	public CategoryDto getCategoryById(Integer categoryId);
	
	public List<CategoryDto> getAllCategory();

}
