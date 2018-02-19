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
			if(book.getFile().isEmpty()) {
				errors.rejectValue("file", "book.file", "no puede esta vacio");
			}
			if(!book.getFile().getContentType().contains("pdf")) {
				errors.rejectValue("file", "book.file", "debe ser del tipo pdf");
			}
		}
	}
}