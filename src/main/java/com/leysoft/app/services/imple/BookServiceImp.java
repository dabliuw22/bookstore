package com.leysoft.app.services.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leysoft.app.entitys.Book;
import com.leysoft.app.repositorys.BookRespository;
import com.leysoft.app.services.inter.BookService;

@Service
public class BookServiceImp implements BookService {

	@Autowired
	private BookRespository bookRespository;
	
	@Transactional
	@Override
	public void save(Book book) {
		bookRespository.save(book);
	}

	@Transactional(readOnly = true)
	@Override
	public Book findById(Long id) {
		return bookRespository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Book findByNombre(String nombre) {
		return bookRespository.findByNombre(nombre);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Book> findByNombreContaining(String nombre) {
		return bookRespository.findByNombreContaining(nombre);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Book> findByAutorContaining(String autor) {
		return bookRespository.findByAutorContaining(autor);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Book> findAll() {
		return (List<Book>) bookRespository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookRespository.findAll(pageable);
	}
	
	@Transactional
	@Override
	public void update(Book book) {
		bookRespository.save(book);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Book book = findById(id);
		if(book != null) {
			bookRespository.delete(id);
		}
	}
}