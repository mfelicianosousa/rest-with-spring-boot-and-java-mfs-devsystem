package com.mfsdevsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mfsdevsystem.data.vo.v1.BookVO;
import com.mfsdevsystem.exception.RequiredObjectIsNullException;
import com.mfsdevsystem.model.Book;
import com.mfsdevsystem.repositories.BookRepository;
import com.mfsdevsystem.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;

	@InjectMocks
	private BookServices service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(input);
	}

	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();

		assertNotNull(books);
		assertEquals(14, books.size());

		var bookOne = books.get(1);
		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		// System.out.println(result.toString());
		assertNotNull(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", bookOne.getAuthor());
		assertEquals("Some Title1", bookOne.getTitle());
		assertEquals(25D, bookOne.getPrice());
		assertNotNull(bookOne.getLauchDate());

		var bookFour = books.get(4);
		assertNotNull(bookFour);
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		// System.out.println(result.toString());
		assertNotNull(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Some Author4", bookFour.getAuthor());
		assertEquals("Some Title4", bookFour.getTitle());
		assertEquals(25D, bookFour.getPrice());
		assertNotNull(bookFour.getLauchDate());
		
		var bookSeven = books.get(7);
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		// System.out.println(result.toString());
		assertNotNull(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Some Author7", bookSeven.getAuthor());
		assertEquals("Some Title7", bookSeven.getTitle());
		assertEquals(25D, bookSeven.getPrice());
		assertNotNull(bookSeven.getLauchDate());
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		// System.out.println(result.toString());
		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLauchDate());
	}

	@Test
	void testCreate() {
		Book entity = input.mockEntity(1);
		Book persisted = entity;
		persisted.setId(1L);

		BookVO bookVO = input.mockVO(1);
		bookVO.setKey(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(bookVO);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLauchDate());
	}

	@Test
	void testCreateWithNullBook() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertNotNull(actualMessage.contains(expectedMessage));

	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1);
		Book persisted = entity;
		persisted.setId(1L);

		BookVO bookVO = input.mockVO(1);
		bookVO.setKey(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(bookVO);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLauchDate());
	}

	@Test
	void testUpdateWithNullBook() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertNotNull(actualMessage.contains(expectedMessage));

	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		service.delete(1L);
	}

}
