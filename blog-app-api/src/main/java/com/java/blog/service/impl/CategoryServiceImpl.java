package com.java.blog.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.java.blog.entities.Category;
import com.java.blog.exception.ResourceNotFoundException;
import com.java.blog.payload.CategoryDto;
import com.java.blog.repository.CategoryRepository;
import com.java.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCat=categoryRepository.save(cat);
		
		CategoryDto catDto=this.modelMapper.map(addedCat, CategoryDto.class);
		return catDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat= this.categoryRepository.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category category=this.categoryRepository.save(cat);
		
		CategoryDto categoryDto2=this.modelMapper.map(category,CategoryDto.class);
		return categoryDto2;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepository.delete(cat);
		
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategory=this.categoryRepository.findAll();
		List<CategoryDto> allCategoryDtos=allCategory.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategoryDtos;
	}

}
