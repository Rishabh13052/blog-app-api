package com.java.blog.service;

import java.util.List;

import com.java.blog.payload.UserDto;

public interface UserService {

	public UserDto registerNewUser(UserDto userDto);
	public UserDto createUser(UserDto user); 
	public UserDto updateUser(UserDto user, Integer userId);
	public UserDto getUserById(Integer userId);
	public List<UserDto> getAllUser();
	public void deleteUser(Integer userId);
	
	

}
