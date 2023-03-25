package com.java.blog.controller;

import com.java.blog.exception.ApiException;
import com.java.blog.payload.UserDto;
import com.java.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.blog.payload.JWTAuthResponse;
import com.java.blog.payload.JwtAuthRequest;
import com.java.blog.security.JWTTokenHelper;

@RestController
@RequestMapping("/api/jwttoken/auth")
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(
			@RequestBody JwtAuthRequest jwtAuthRequest) throws ApiException {
		
		System.out.println("A");
		this.authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
		System.out.println("B");
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
		System.out.println("userDetails.getUsername()");

		System.out.println(userDetails.getUsername());

		String generateTokenString=this.jwtTokenHelper.generateToken(userDetails);
		
		System.out.println("D");
		JWTAuthResponse response=new JWTAuthResponse();
		System.out.println("E");
		response.setToken(generateTokenString);
		System.out.println("D");
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws ApiException{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		try{
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch(BadCredentialsException e){
			System.out.println("invalid details");
			throw new ApiException("Invalid username or password!!");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredUser=this.userService.registerNewUser(userDto);
		return new ResponseEntity<>(registeredUser,HttpStatus.OK);
	}
	
}
