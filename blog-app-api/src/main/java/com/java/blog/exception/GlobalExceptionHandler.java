package com.java.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.java.blog.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgsNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> respMap= new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
		 String fieldError=((FieldError) error).getField();
		 String fieldPsw=error.getDefaultMessage();
		 respMap.put(fieldError, fieldPsw);
		});

		 return new ResponseEntity<Map<String, String>>(respMap,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> ApiExceptionHandler(ApiException ex){
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
}
