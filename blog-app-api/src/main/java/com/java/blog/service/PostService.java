package com.java.blog.service;

import java.util.List;
import java.util.Set;

import com.java.blog.entities.Category;
import com.java.blog.entities.User;
import com.java.blog.payload.PostDto;
import com.java.blog.payload.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	public PostDto updatePost(PostDto postDto, Integer postId);
	public void deletePost(Integer postId);
	public PostDto getPostById(Integer postId);
	public List<PostDto> getAllPost();
	public List<PostDto> getAllPostByUser(Integer userId);
	public List<PostDto> getAllPostByCategory(Integer categoryId);
	public List<PostDto> searchPost(String keyword);
	public List<PostDto> getAllPostPagination(Integer pageNumber, Integer pageSize);
	public PostResponse getAllPostPaginationSort(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	public PostResponse getAllPostByPostResponse(Integer pageNumber, Integer pageSize);
	public PostResponse getAllPostByUserPagination(Integer userId, Integer pageNumber, Integer pageSize);
	public PostResponse getAllPostByCategoryPagination(Integer categoryId, Integer pageNumber, Integer pageSize);
}
