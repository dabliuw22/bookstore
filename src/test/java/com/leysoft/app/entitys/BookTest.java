package com.leysoft.app.entitys;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	
	private Book book;
	
	@Before
	public void init() {
		this.book = new Book();
	}

	@Test
	public void testGetId() {
		Long id = 10L;
		book.setId(id);
		assertEquals(id, book.getId());
	}

	@Test
	public void testGetNombre() {
		String nombre = "TestName";
		book.setNombre(nombre);
		assertEquals(nombre, book.getNombre());
	}

	@Test
	public void testGetAutor() {
		String autor = "TestAutor";
		book.setAutor(autor);
		assertEquals(autor, book.getAutor());
	}

	@Test
	public void testGetDescripcion() {
		String descripcion = "TestDescripcion";
		book.setDescripcion(descripcion);
		assertEquals(descripcion, book.getDescripcion());
	}

	@Test
	public void testGetFechaPublicacion() {
		Date fecha = new Date();
		book.setFechaPublicacion(fecha);
		assertEquals(fecha, book.getFechaPublicacion());
	}

	@Test
	public void testGetLenguaje() {
		String lenguaje = "TestLenguaje";
		book.setLenguaje(lenguaje);
		assertEquals(lenguaje, book.getLenguaje());
	}

	@Test
	public void testGetCategoria() {
		String categoria = "TestCategoria";
		book.setCategoria(categoria);
		assertEquals(categoria, book.getCategoria());
	}

	@Test
	public void testGetNumeroPaginas() {
		Integer numeroPaginas = 200;
		book.setNumeroPaginas(numeroPaginas);
		assertEquals(numeroPaginas, book.getNumeroPaginas());
	}

	@Test
	public void testGetPrecio() {
		Double precio = 10D;
		book.setPrecio(precio);
		assertEquals(precio, book.getPrecio());
	}

	@Test
	public void testGetPrecioEnvio() {
		Double precioEnvio = 10D;
		book.setPrecioEnvio(precioEnvio);
		assertEquals(precioEnvio, book.getPrecioEnvio());
	}

	@Test
	public void testGetStock() {
		Integer stock = 1000;
		book.setStock(stock);
		assertEquals(stock, book.getStock());
	}

	@Test
	public void testIsActivo() {
		boolean activo = true;
		book.setActivo(activo);
		assertEquals(activo, book.isActivo());
	}
}