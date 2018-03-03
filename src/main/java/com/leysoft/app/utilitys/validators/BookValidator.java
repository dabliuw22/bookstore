package com.leysoft.app.utilitys.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leysoft.app.entitys.Book;

@Component
public class BookValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Book book = (Book) obj;
		if(book != null) {
			if(book.getFileArchivo().isEmpty()) {
				errors.rejectValue("fileArchivo", "book.fileArchivo", "no puede esta vacio");
			}
			if(!book.getFileArchivo().getContentType().contains("pdf")) {
				errors.rejectValue("fileArchivo", "book.fileArchivo", "debe ser del tipo pdf");
			}
			if(book.getFileImagen().isEmpty()) {
				errors.rejectValue("fileImagen", "book.fileImagen", "no puede esta vacio");
			}
			if(!book.getFileImagen().getContentType().contains("image")) {
				errors.rejectValue("fileImagen", "book.fileImagen", "debe ser del tipo image");
			}
		}
	}
}