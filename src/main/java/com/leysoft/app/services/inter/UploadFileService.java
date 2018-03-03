package com.leysoft.app.services.inter;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
	
	public String save(MultipartFile file) throws IOException;
	
	public void delete(String imagen, String archivo);
	
	public void deleteAll();
	
	public void init() throws IOException;
}
