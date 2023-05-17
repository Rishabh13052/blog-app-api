package com.java.blog;

import com.java.blog.entities.*;
import com.java.blog.payload.CategoryDto;
import com.java.blog.payload.PostDto;
import com.java.blog.payload.RoleDto;
import com.java.blog.payload.UserDto;
import com.java.blog.repository.CategoryRepository;
import com.java.blog.repository.PostRepository;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.CategoryService;
import com.java.blog.service.PostService;
import com.java.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
class BlogAppApiApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@MockBean
	ModelMapper modelMapper;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private PostRepository postRepository;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@MockBean
	PasswordEncoder passwordEncoder;

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
	public void createPostTest() {
		User user = new User(1, "Rishabh", "Rish@xyz.com", "abc", "This is Rishabh", new ArrayList<>(), null, null);
		Category category = new Category(1, "Cat1", "This is Category", null);

		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

		Post post = new Post(1, "catPost", "This is post", "abc.jpeg", "Today's Date", new Category(), new User(), new ArrayList<>());
		PostDto postDto = new PostDto(1, "catPost", "This is post", "abc.jpeg", "Today's Date", new CategoryDto(), new UserDto(), new ArrayList<>());
		Mockito.when(modelMapper.map(postDto,Post.class)).thenReturn(post);

		Mockito.when(postRepository.save(post)).thenReturn(post);
		Mockito.when(modelMapper.map(post,PostDto.class)).thenReturn(postDto);

		PostDto createdPostDto=this.postService.createPost(postDto, user.getId(), category.getCategoryId());
		assertEquals(createdPostDto,postDto);

	}


	@Test
	public void registerNewUserTest(){
		User user=new User(1,"Rishabh","rishabh@xyz.com","abcdef","This is Rishabh",null,null,new ArrayList<>());
		UserDto userDto=new UserDto(1,"Rishabh","rishabh@xyz.com","abcdef","This is Rishabh",null,new ArrayList<>());

		Mockito.when(modelMapper.map(userDto,User.class)).thenReturn(user);
		Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(null);

		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

		UserDto createdUser=this.userService.registerNewUser(userDto);

		assertEquals(createdUser,userDto);
	}

	@Test
	void contextLoads() {
	}

}
