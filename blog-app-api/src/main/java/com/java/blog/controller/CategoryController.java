package com.java.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.blog.payload.ApiResponse;
import com.java.blog.payload.CategoryDto;
import com.java.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.OK);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updatedCategoryDto=this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId ){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Succesfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		CategoryDto getCategoryDto=this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(getCategoryDto,HttpStatus.OK);
	} 
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAllCatgory(){
		List<CategoryDto> categoriesDtos=this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(categoriesDtos,HttpStatus.OK);
	}
	
}
