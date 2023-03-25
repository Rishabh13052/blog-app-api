package com.java.blog.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.java.blog.entities.Comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message="Username must be min 4 character")
	private String name;
	
	@Email(message="Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=4,max=50, message="password must be min of 4 char and maximum of 50 char")
	private String password;
	
	@NotEmpty
	private String about;
	
	private List<CommentDto> comments=new ArrayList();

	private List<RoleDto> roles=new ArrayList();
}
