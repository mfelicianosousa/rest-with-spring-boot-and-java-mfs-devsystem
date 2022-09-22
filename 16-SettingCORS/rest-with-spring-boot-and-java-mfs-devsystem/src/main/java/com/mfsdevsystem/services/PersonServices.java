package com.mfsdevsystem.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsdevsystem.controllers.PersonController;
import com.mfsdevsystem.data.vo.v1.PersonVO;
import com.mfsdevsystem.exception.RequiredObjectIsNullException;
import com.mfsdevsystem.exception.ResourceNotFoundException;
import com.mfsdevsystem.mapper.DozerMapper;
import com.mfsdevsystem.model.Person;
import com.mfsdevsystem.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName()) ;
	
	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {
		logger.info("Finding All people!");
		
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
		
		persons
		  .stream()
		  .forEach(p -> p.add(linkTo( methodOn( PersonController.class ).findById( p.getKey() )).withSelfRel()));
		
		return persons;
		
	}
	
	public PersonVO findById(Long key) {
		logger.info("Finding one person!");
			
		var entity = repository.findById(key)
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
		
		PersonVO personVO = DozerMapper.parseObject(entity, PersonVO.class) ;
		personVO.add( linkTo( methodOn( PersonController.class ).findById( key )).withSelfRel());
		return personVO ;
	}
	
	
	public PersonVO create( PersonVO person ) {
		
		if ( person == null ) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class) ;
		var personVO = DozerMapper.parseObject( repository.save(entity), PersonVO.class) ;
		personVO.add( linkTo( methodOn( PersonController.class ).findById( personVO.getKey() )).withSelfRel());
		return personVO ;
		
	}
	
	public PersonVO update(PersonVO person) {
		
		if ( person == null ) throw new RequiredObjectIsNullException();
		
		logger.info("Update person!");
		var entity = repository.findById(person.getKey())
		.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
        
		entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
		
        var personVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class) ;
    	personVO.add( linkTo( methodOn( PersonController.class ).findById( personVO.getKey() )).withSelfRel());

		return personVO ;
	}
	
		
	public void delete(Long key) {
		
		logger.info("Deleting one person!");
		var entity = repository.findById( key )
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
	
		repository.delete(entity);
	}
	
	
}
