package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    //atributos

    private BookRepository bookRepository;
    //constructores

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // CRUD sobre la entidad Book


    // Buscar todos los libros que hay en la base de datos ArrayList de Libros
    @GetMapping("/api/books")
    public List<Book> findAll(){
        // recuperar y devolver los libros de base de datos
        return bookRepository.findAll();
    }

    //buscar un solo libro en case de datos segun su ID
    //http: //localhost:8080/api/books/1
    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findOneById(@PathVariable Long id){
        Optional<Book> bookOpt = bookRepository.findById(id); //4456
        //podemos comprobar sobre el opcional si existe resultado

        //opcion1
//        if(bookOpt.isPresent())
//            return bookOpt.get();
//        else
//            return null;
//        //opcion2
//        return bookOpt.orElse(null);

        //opcion 3
//        if(bookOpt.isPresent())
//            return ResponseEntity.ok(bookOpt.get());
//        else
//            return ResponseEntity.notFound().build();
        //opcion 4
        return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear un nuevo libo en base de datos
    // metodo POST, no colisiona con findAll porque son diferentes metodos HTTP: GET vs POST
    @PostMapping("/api/books/")
    public Book create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        //guadar el libro recibio por parametro en la base de datos
        return bookRepository.save(book); // el libro devuelto tiene una clave primary
    }



    //actualizar un libro  existente en base de datos
    @PutMapping("/api/books")
    public Book update(@RequestBody Book book){
        return book;
    }

    // borrar un libro en base de datos

}
