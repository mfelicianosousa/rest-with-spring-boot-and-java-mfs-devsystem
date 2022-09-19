package com.mfsdevsystem.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsdevsystem.data.vo.v1.PersonVO;
import com.mfsdevsystem.data.vo.v2.PersonVOV2;
import com.mfsdevsystem.exception.ResourceNotFoundException;
import com.mfsdevsystem.mapper.DozerMapper;
import com.mfsdevsystem.mapper.custom.PersonMapper;
import com.mfsdevsystem.model.Person;
import com.mfsdevsystem.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName()) ;
	
	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		logger.info("Finding All people!");
		
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
	}
	


	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
			
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
		
		return DozerMapper.parseObject(entity, PersonVO.class) ;
	}
	
	
	public PersonVO create( PersonVO person ) {
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class) ;
		var personVO = DozerMapper.parseObject( repository.save(entity), PersonVO.class) ;
		
		return personVO ;
		
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		logger.info("Creating one person with V2!");
		
		var entity = mapper.convertVoToEntity(person);
		var personVO = mapper.convertEntityToVO(repository.save(entity));
		
		return personVO ;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Update person!");
		var entity = repository.findById(person.getId())
		.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
        
		entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
		
        var personVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class) ;
		return personVO ;
	}
	
		
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		var entity = repository.findById( id )
				.orElseThrow(()-> new ResourceNotFoundException("No records found this ID") );
	
		repository.delete(entity);
	}



	
	
	
}
