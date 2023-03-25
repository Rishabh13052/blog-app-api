package com.java.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.blog.entities.User;
import com.java.blog.exception.ResourceNotFoundException;
import com.java.blog.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user by DataBase
		User user =this.userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email", username));
		
		return user;
	}
	
}
