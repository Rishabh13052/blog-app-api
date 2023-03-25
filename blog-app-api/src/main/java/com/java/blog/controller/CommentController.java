package com.java.blog.controller;

import java.security.KeyStore.PrivateKeyEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.blog.payload.ApiResponse;
import com.java.blog.payload.CommentDto;
import com.java.blog.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/add/post/{postId}/user/{userId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId,
			@PathVariable Integer userId){
		CommentDto comment=this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment Deleted Successfully",true),HttpStatus.OK);
	}
}
