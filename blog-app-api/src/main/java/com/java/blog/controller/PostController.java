package com.java.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.blog.config.AppConstants;
import com.java.blog.payload.ApiResponse;
import com.java.blog.payload.PostDto;
import com.java.blog.payload.PostResponse;
import com.java.blog.service.FileService;
import com.java.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/test")
	public String temp() {
		return "hi";
	}
	
	@PostMapping("/create/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId){
		System.out.println("3");
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		System.out.println("4");
		 return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("posts/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> posts= this.postService.getAllPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("posts/user/{userId}") 
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> posts=this.postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/getallPosts")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> posts=this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/get/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto post=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/post/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post Deleted Succesfully",true);
	}
	
	@PutMapping("/update/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
		PostDto post=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	/*
	 * @GetMapping("/post/psize/{pageSize}/pnumber/{pageNumber}") public
	 * ResponseEntity<List<PostDto>> getAllPostByPagination(@PathVariable Integer
	 * pageNumber, @PathVariable Integer pageSize){ List<PostDto>
	 * posts=this.postService.getAllPostPagination(pageNumber, pageSize); return new
	 * ResponseEntity<List<PostDto>>(posts,HttpStatus.OK); }
	 */
	
	@GetMapping("/post/pagination")
	public ResponseEntity<List<PostDto>> getAllPostByPagination(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required =false)Integer pageSize)
	
	{
		List<PostDto> posts=this.postService.getAllPostPagination(pageNumber, pageSize);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/post/pagination/postresponse")
	public ResponseEntity<PostResponse> getAllPostByPostResponse(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required =false)Integer pageSize)
	
	{
		PostResponse postResponse=this.postService.getAllPostByPostResponse(pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/post/pagination/sort/postresponse")
	public ResponseEntity<PostResponse> getAllPostByPostResponseBySort(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required =false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required =false)String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required =false)String sortDir)
	
	{
		PostResponse postResponse=this.postService.getAllPostPaginationSort(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts/pagination/category/{categoryId}")
	public ResponseEntity<PostResponse> getPostByCategoryUsingPagination(@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required =false)Integer pageSize) 
	{
		PostResponse postResponse= this.postService.getAllPostByCategoryPagination(categoryId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts/pagination/user/{userId}")
	public ResponseEntity<PostResponse> getPostByUserUsingPagination(@PathVariable Integer userId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required =false)Integer pageSize) 
	{
		PostResponse postResponse= this.postService.getAllPostByUserPagination(userId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByKeyword(@PathVariable String keyword){
		List<PostDto> posts=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//upload image
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);		
		postDto.setImageName(fileName);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	//method to serve files
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException{
		InputStream resourceInputStream=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceInputStream,response.getOutputStream());
		
		}

}
