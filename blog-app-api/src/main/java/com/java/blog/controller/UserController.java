package com.java.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.blog.payload.ApiResponse;
import com.java.blog.payload.UserDto;
import com.java.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED); 
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
		UserDto updateUserDto=this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUserDto);
	}
	//giving authority to Admin to delete User
	@PreAuthorize("hasRole('ADMIN')")

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userid){
		this.userService.deleteUser(userid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK); 
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	
}
