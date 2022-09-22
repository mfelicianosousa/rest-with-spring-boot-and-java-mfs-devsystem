package com.mfsdevsystem.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsdevsystem.controllers.BookController;
import com.mfsdevsystem.controllers.PersonController;
import com.mfsdevsystem.data.vo.v1.BookVO;
import com.mfsdevsystem.exception.RequiredObjectIsNullException;
import com.mfsdevsystem.exception.ResourceNotFoundException;
import com.mfsdevsystem.mapper.DozerMapper;
import com.mfsdevsystem.model.Book;
import com.mfsdevsystem.repositories.BookRepository;

@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName()) ;
	
	@Autowired
	BookRepository repository;
	
	public List<BookVO> findAll() {
		logger.info("Finding All Book!");
		
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class) ;
		
		books
		  .stream()
		  .forEach(b -> b.add(linkTo( methodOn( BookController.class ).findById( b.getKey() )).withSelfRel()));
		
		return books;
		
	}
	
	public BookVO findById(Long id) {
		logger.info("Finding one book!");
			
		var entity = repository.findById( id )
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
		
		BookVO bookVO = DozerMapper.parseObject(entity, BookVO.class) ;
		bookVO.add( linkTo( methodOn( BookController.class ).findById( id )).withSelfRel());
		return bookVO ;
	}
	
	
	public BookVO create( BookVO book ) {
		
		if ( book == null ) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(book, Book.class) ;
		var bookVO = DozerMapper.parseObject( repository.save(entity), BookVO.class) ;
		bookVO.add( linkTo( methodOn( PersonController.class ).findById( bookVO.getKey() )).withSelfRel());
		return bookVO ;
		
	}
	
	public BookVO update(BookVO book) {
		
		if ( book == null ) throw new RequiredObjectIsNullException();
		
		logger.info("Update book!");
		var entity = repository.findById( book.getKey())
		.orElseThrow(()-> new ResourceNotFoundException("No records found this Id") );
        
		entity.setAuthor(book.getAuthor());
		entity.setLauchDate(book.getLauchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
       	
        var bookVO = DozerMapper.parseObject(repository.save(entity), BookVO.class) ;
    	bookVO.add( linkTo( methodOn( BookController.class ).findById( bookVO.getKey() )).withSelfRel());

		return bookVO ;
	}
	
		
	public void delete(Long key) {
		
		logger.info("Deleting one book!");
		var entity = repository.findById( key )
				.orElseThrow(()-> new ResourceNotFoundException("No records found this Id") );
	
		repository.delete(entity);
	}
}
