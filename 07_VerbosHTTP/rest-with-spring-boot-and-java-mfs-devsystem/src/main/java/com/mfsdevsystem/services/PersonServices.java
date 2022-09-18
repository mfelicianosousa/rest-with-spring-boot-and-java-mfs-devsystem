package com.mfsdevsystem.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsdevsystem.exception.ResourceNotFoundException;
import com.mfsdevsystem.model.Person;
import com.mfsdevsystem.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName()) ;
	
	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		logger.info("Finding All people!");
		
		return repository.findAll();
	}
	


	public Person findById(Long id) {
		logger.info("Finding one person!");
			
		return repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
	}
	
	
	public Person create( Person person ) {
		
		logger.info("Creating one person!");
		
		return repository.save( person ) ;
	}
	
	public Person update(Person person) {
		
		logger.info("Update person!");
		Person entity = repository.findById(person.getId())
		.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
		
		return repository.save(entity) ;
	}
	
		
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		Person entity = repository.findById( id )
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
		repository.delete(entity);
	}
}
