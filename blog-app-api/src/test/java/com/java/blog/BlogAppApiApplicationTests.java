package com.java.blog;

import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.Role;
import com.java.blog.entities.User;
import com.java.blog.payload.CategoryDto;
import com.java.blog.repository.CategoryRepository;
import com.java.blog.repository.PostRepository;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.CategoryService;
import com.java.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
class BlogAppApiApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private PostRepository postRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void getAllCategoryTest(){
		when(categoryRepository.findAll())
		.thenReturn(List.of(new Category(1,"Pen","This is pen", List.of(new Post(1,"PenPost","This is good","abc.png","Today'sDate",new Category(),new User(),null))),
				new Category(2,"Pencil","This is pencil",List.of(new Post(1,"PenPost","This is good","abc.png","Today'sDate",new Category(),new User(),null)))));

		System.out.println(categoryRepository.findAll().size());
		assertEquals(2,categoryService.getAllCategory().size());

	}

	@Test
	public void getCategoryByIdTest(){
		when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category(1, "Pencil", "This is Pencil", null)));
		assertEquals("Pencil",categoryService.getCategoryById(1).getCategoryTitle());
	}

	@Test
	public void deleteCategoryTest(){
		Category category=new Category(2,"eraser","This is Eraser",null);
		Mockito.when(categoryRepository.findById(3)).thenReturn(Optional.of(category));
		categoryService.deleteCategory(3);
		verify(categoryRepository, times(1)).delete(category);
	}

	@Test
	public void getAllPostByUserTest(){
		User user= new User(1,"Rishabh","abc@gmail.com","abcdef","This is about",List.of(new Post(1,"post","post","date","abc.jpeg",null,new User(1,"","","","",null,null,null),null),new Post(2,"post","post","date","abc.jpeg",null,new User(1,"","","","",null,null,null),null)),null,List.of(new Role(1,"Admin")));
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		List<Post> post=List.of(new Post(1,"post","post","date","abc.jpeg",null,new User(1,"","","","",null,null,null),null),new Post(2,"post","post","date","abc.jpeg",null,new User(1,"","","","",null,null,null),null));
		Mockito.when(postRepository.findByUser(user)).thenReturn(post);
		assertEquals(2,postService.getAllPostByUser(user.getId()).size());
	}

	@Test
	void contextLoads() {
	}

}
