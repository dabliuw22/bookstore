package com.leysoft.app.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
		assertEquals(viewName, adminController.addBook(model));
	}

	@Test
	public void testAddBookBookBindingResultModel() throws IOException {
		String viewName = "redirect:/admin";
		assertEquals(viewName, adminController.addBook(book, errors, model));
		verify(bookService, times(1)).save(book);
	}
}