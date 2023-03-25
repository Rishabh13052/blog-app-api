package com.java.blog.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.User;
import com.java.blog.exception.ResourceNotFoundException;
import com.java.blog.payload.PostDto;
import com.java.blog.payload.PostResponse;
import com.java.blog.repository.CategoryRepository;
import com.java.blog.repository.PostRepository;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		System.out.println("1");
		
		User user=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("abc.png");
		post.setAddedDate(LocalDate.now().toString());
		
		Post newPost=this.postRepository.save(post);
		System.out.println("2");
		
		return this.modelMapper.map(newPost, PostDto.class);
	}
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepository.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
		PostDto postDto=this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> posts=this.postRepository.findAll();
		List<PostDto> getAllPosts=posts.stream().map((post)->(this.modelMapper.map(post, PostDto.class))).toList();
		return getAllPosts;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		List<Post> posts= this.postRepository.findByUser(user);		
		List<PostDto> allPostByUser=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostByUser;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		
		Category cat=this.categoryRepository.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		List<Post> posts=this.postRepository.findByCategory(cat);
		
		List<PostDto> allPostByCategory=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());		
		return allPostByCategory;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts=this.postRepository.findByTitleContaining(keyword);
		List<PostDto> listPosts=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).toList();
		
		return listPosts;
	}


	@Override
	public List<PostDto> getAllPostPagination(Integer pageNumber, Integer pageSize) {
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> posts=this.postRepository.findAll(p);
		
		List<Post> listPosts=posts.getContent();
		List<PostDto> allPost=listPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).toList();
		return allPost;
	}


	@Override
	public PostResponse getAllPostByPostResponse(Integer pageNumber, Integer pageSize) {
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> posts=this.postRepository.findAll(p);
		List<Post> listPosts=posts.getContent();
		
		List<PostDto> allPost=listPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).toList();
		PostResponse postResponse=new PostResponse();
		
		postResponse.setPosts(allPost);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(((int)posts.getTotalElements()));
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		
		return postResponse;
	}


	@Override
	public PostResponse getAllPostByUserPagination(Integer userId, Integer pageNumber, Integer pageSize) {
		User user=this.userRepository.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> posts=this.postRepository.findByUser(user, p);
		
		List<Post> listPosts=posts.getContent();
		
		List<PostDto> allPostByUserPagination=listPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).toList(); 
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPosts(allPostByUserPagination);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(((int)posts.getTotalElements()));
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		return postResponse;
	}


	@Override
	public PostResponse getAllPostByCategoryPagination(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Category cat=this.categoryRepository.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> posts=this.postRepository.findByCategory(cat,p);
		
		List<Post> listPosts=posts.getContent();
		
		List<PostDto> allPostByCategoryPagination=listPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());	
		PostResponse postResponse=new PostResponse();
		
		postResponse.setPosts(allPostByCategoryPagination);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(((int)posts.getTotalElements()));
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		return postResponse;
	}


	@Override
	public PostResponse getAllPostPaginationSort(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		} else sort=Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> posts=this.postRepository.findAll(p);
		List<Post> listPosts=posts.getContent();
		
		List<PostDto> allPost=listPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).toList();
		PostResponse postResponse=new PostResponse();
		
		postResponse.setPosts(allPost);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(((int)posts.getTotalElements()));
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		
		return postResponse;
	}

	

}
