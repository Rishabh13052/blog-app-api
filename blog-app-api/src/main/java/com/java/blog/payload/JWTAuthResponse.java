package com.java.blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JWTAuthResponse {
	private String token;
}
