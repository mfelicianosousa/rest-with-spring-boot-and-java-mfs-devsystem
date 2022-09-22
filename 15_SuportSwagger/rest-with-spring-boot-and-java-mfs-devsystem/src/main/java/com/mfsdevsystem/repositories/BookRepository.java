package com.mfsdevsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfsdevsystem.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
