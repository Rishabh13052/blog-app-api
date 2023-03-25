package com.java.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		//file name
		String name=file.getOriginalFilename();
		
		//generate random file name
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//full path
		String filePath=path+File.separator+fileName1;
				
				
		//create folder if not exist
		File file2=new File(path);
		if(!file2.exists()) {
			file2.mkdir();  
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		String fullPath=path+File.separator+filename;
		InputStream inputStream=new FileInputStream(fullPath);
		
		return inputStream;
	}
	
}
