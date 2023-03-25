package com.java.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	//1. Get Token from Header	
		System.out.println("request "+request);
		String requestToken=request.getHeader("Authorization");

		System.out.println(requestToken);
		
		String userName=null;
		String actualToken=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			 actualToken=requestToken.substring(7);
			try {
			userName=this.jwtTokenHelper.extractUsername(actualToken);
			}catch(IllegalArgumentException e){
				System.out.println("Unable to get Token");
			}catch(ExpiredJwtException ex){
				System.out.println("Token expired");
			}catch(MalformedJwtException e){
				System.out.println("Invalid JWt Token");
			}
		}
		else {
			System.out.println("Token doesn't begin with Bearer");
		}
		
	//2. After getting tokens, validate Token
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(userName);
			if(this.jwtTokenHelper.validateToken(actualToken, userDetails)) {
				//Everything is fine & set Authentication
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				System.out.println("Invalid Jwt Token!!");
			}
		}else {
			System.out.println("Username or Context is not found");
		}
		
		filterChain.doFilter(request, response);
	}



}
