package com.java.blog.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.java.blog.entities.Category;
import com.java.blog.entities.Comment;
import com.java.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private String addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comments=new ArrayList();
	
	
}
