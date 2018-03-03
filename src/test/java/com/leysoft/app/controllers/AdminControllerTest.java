package com.leysoft.app.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.leysoft.app.entitys.Book;
import com.leysoft.app.services.inter.BookService;
import com.leysoft.app.services.inter.UploadFileService;
import com.leysoft.app.utilitys.validators.BookValidator;

public class AdminControllerTest {
	
	@Mock
	private BookService bookService;
	
	@Mock
	private Model model;
	
	@Mock
	private Book book;
	
	@Mock
	private BookValidator bookValidator;
	
	@Mock
	private UploadFileService uploadFileService;
	
	@Mock
	private BindingResult errors;
	
	@InjectMocks
	private AdminController adminController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDashboard() {
		String viewName = "admin/dashboard";
		assertEquals(viewName, adminController.dashboard());
	}

	@Test
	public void testAddBookModel() {
		String viewName = "admin/book/create";
		String attributeName = "book";
		assertEquals(viewName, adminController.addBook(model));
		verify(model, times(1)).addAttribute(attributeName, new Book());
	}

	@Test
	public void testAddBookBookBindingResultModel() throws IOException {
		String viewName = "redirect:/admin";
		String attributeName = "book";
		ArgumentCaptor<MultipartFile> multipartFileArgument = ArgumentCaptor.forClass(MultipartFile.class);
		assertEquals(viewName, adminController.addBook(book, errors, model));
		verify(errors, times(1)).hasErrors();
		verify(uploadFileService, times(2)).save(multipartFileArgument.capture());
		verify(bookService, times(1)).save(book);
		verify(model, times(0)).addAttribute(attributeName, book);
	}
}