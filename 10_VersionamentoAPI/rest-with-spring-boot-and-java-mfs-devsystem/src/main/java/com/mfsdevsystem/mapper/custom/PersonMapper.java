package com.mfsdevsystem.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.mfsdevsystem.data.vo.v2.PersonVOV2;
import com.mfsdevsystem.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVO(Person person) {
		PersonVOV2 personVO = new PersonVOV2();
		personVO.setId(person.getId());
		personVO.setAddress(person.getAddress());
		personVO.setBirthDay(new Date());
		personVO.setFirstName(person.getFirstName());
		personVO.setLastName(person.getLastName());
		personVO.setGender(person.getGender());
		return personVO;
	}
	
	public Person convertVoToEntity(PersonVOV2 person) {
		Person 	entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		//entity.setBirthDay(new Date());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}
}
