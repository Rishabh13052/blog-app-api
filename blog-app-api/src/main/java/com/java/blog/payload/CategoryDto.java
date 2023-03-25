package com.java.blog.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=3,max=10,message = "title must be min of 3 char and max of 10 char")
	private String categoryTitle;
	
	@NotEmpty
	private String categoryDescription;
	
}