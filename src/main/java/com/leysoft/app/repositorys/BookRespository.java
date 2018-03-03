package com.leysoft.app.repositorys;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leysoft.app.entitys.Book;

public interface BookRespository extends PagingAndSortingRepository<Book, Long> {
	
	public Book findByNombre(String nombre);
	
	public List<Book> findByNombreContaining(String nombre);
	
	public List<Book> findByAutorContaining(String autor);
}