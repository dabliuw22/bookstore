package com.leysoft.app.services.inter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leysoft.app.entitys.Book;

public interface BookService {
	
	public void save(Book book);
	
	public Book findById(Long id);
	
	public Book findByNombre(String nombre);
	
	public List<Book> findByNombreContaining(String nombre);
	
	public List<Book> findByAutorContaining(String autor);
	
	public List<Book> findAll();
	
	public Page<Book> findAll(Pageable pageable);
	
	public void update(Book book);
	
	public void delete(Long id);
}