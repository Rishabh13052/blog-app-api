package com.java.blog.config;

import com.java.blog.entities.Role;
import com.java.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ModelMap implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("1234"));

		try{
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles=List.of(role,role1);
			List<Role> result=this.roleRepository.saveAll(roles);


		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
