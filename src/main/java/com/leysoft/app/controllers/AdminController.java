package com.leysoft.app.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leysoft.app.entitys.Book;
import com.leysoft.app.services.inter.BookService;
import com.leysoft.app.services.inter.UploadFileService;
import com.leysoft.app.utilitys.validators.BookValidator;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private BookValidator bookValidator;
	
	@GetMapping(value = "")
	public String dashboard() {
		return "admin/dashboard";
	}
	
	@GetMapping(value = "/book/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		return "admin/book/create";
	}
	
	@PostMapping(value = "/book/add")
	public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult errors, 
			Model model) throws IOException {
		bookValidator.validate(book, errors);
		if(!errors.hasErrors()) {
			String archivo = uploadFileService.save(book.getFileArchivo());
			String imagen = uploadFileService.save(book.getFileImagen());
			book.setArchivo(archivo);
			book.setImagen(imagen);
			bookService.save(book);
			return "redirect:/admin";
		}
		model.addAttribute("book", book);
		return "admin/book/create";
	}
}
