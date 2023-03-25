package com.java.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	private List<PostDto> posts;
	private int pageNumber;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean lastPage;
	

}
