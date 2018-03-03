package com.leysoft.app.services.imple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.leysoft.app.services.inter.UploadFileService;

@Service
public class UploadFileServiceImp implements UploadFileService {
	
	private static final String MEDIA_FOLDER = "media";
	private static final String FILE_FOLDER = "file";

	@Override
	public String save(MultipartFile file) throws IOException {
		String fileNombre = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
		Path path = getPath(fileNombre, file.getContentType());
		Files.copy(file.getInputStream(), path);
		return fileNombre;
	}

	@Override
	public void delete(String imagen, String archivo) {
		Path pathImagen = Paths.get(MEDIA_FOLDER).resolve(imagen).toAbsolutePath();
		Path pathArchivo = Paths.get(FILE_FOLDER).resolve(archivo).toAbsolutePath();
		if(pathImagen.toFile().exists() && pathImagen.toFile().canRead()) {
			pathImagen.toFile().delete();
		}
		if(pathArchivo.toFile().exists() && pathArchivo.toFile().canRead()) {
			pathArchivo.toFile().delete();
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(MEDIA_FOLDER).toFile());
		FileSystemUtils.deleteRecursively(Paths.get(FILE_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectories(Paths.get(MEDIA_FOLDER));
		Files.createDirectories(Paths.get(FILE_FOLDER));
	}
	
	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")); 
	}
	
	private Path getPath(String fileNombre, String type) {
		if(type.contains("image")) {
			return Paths.get(MEDIA_FOLDER).resolve(fileNombre).toAbsolutePath();
		} else {
			return Paths.get(FILE_FOLDER).resolve(fileNombre).toAbsolutePath();
		}
	}
}