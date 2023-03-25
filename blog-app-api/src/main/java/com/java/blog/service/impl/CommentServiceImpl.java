package com.java.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.blog.config.ModelMap;
import com.java.blog.entities.*;
import com.java.blog.exception.ResourceNotFoundException;
import com.java.blog.payload.CommentDto;
import com.java.blog.repository.CommentRepository;
import com.java.blog.repository.PostRepository;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		
		Post post=this.postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		
		User user=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		Comment comment=this.modelMapper.map(commentDto,Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment=this.commentRepository.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment=this.commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		
		this.commentRepository.delete(comment);
	}

}
