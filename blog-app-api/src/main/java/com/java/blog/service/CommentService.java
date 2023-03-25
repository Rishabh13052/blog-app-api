package com.java.blog.service;

import com.java.blog.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	
}
