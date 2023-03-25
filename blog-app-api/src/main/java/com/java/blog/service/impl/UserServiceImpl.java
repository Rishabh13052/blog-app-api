package com.java.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.java.blog.config.AppConstants;
import com.java.blog.entities.Role;
import com.java.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.blog.entities.User;
import com.java.blog.exception.ResourceNotFoundException;
import com.java.blog.payload.UserDto;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto,User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		Role role=this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);

		User newUser=this.userRepository.save(user);

		return this.modelMapper.map(newUser,UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=this.dtoToUser(userDto);
		User saveduser=userRepository.save(user);
		return this.userToDto(saveduser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		User updatedUser=this.userRepository.save(user);
		UserDto userDto1=this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		return this.userToDto(user); 
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUsers=this.userRepository.findAll();
		List<UserDto> allUserDto=allUsers.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return allUserDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id", userId));
		this.userRepository.deleteById(userId);
	}
	
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		/*
		 * User user =new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 */
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		/*
		 * UserDto userDto =new UserDto(); userDto.setId(user.getId());
		 * userDto.setName(user.getName()); userDto.setEmail(user.getEmail());
		 * userDto.setPassword(user.getPassword()); userDto.setAbout(user.getAbout());
		 */
		
		return userDto;
	}
		
}
