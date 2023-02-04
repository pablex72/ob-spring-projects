package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {



	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//crear libro
		Book book1 = new Book(null, "La montania", "Juan Pablo", 300, 300.3, LocalDate.of(2000,12,12),true);
		Book book2 = new Book(null, "El rio", "Luis Juan", 500, 500.3, LocalDate.of(2002,12,12),true);
		//almacenar un libro

		System.out.println("NUM LIBROS EN BD : " + repository.findAll().size());

		repository.save(book1);
		repository.save(book2);
		//recuperar toods los libros

		System.out.println("NUM LIBROS EN BD : " +repository.findAll().size());

		//borrar un libro
		repository.deleteById(1L);
		System.out.println("NUM LIBROS EN BD : " +repository.findAll().size());

	}

}
