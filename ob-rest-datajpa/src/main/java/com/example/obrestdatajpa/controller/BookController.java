package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    //atributos
    private final Logger log = LoggerFactory.getLogger(BookController.class);

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
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        //guadar el libro recibio por parametro en la base de datos
        // si todo va bien en el if no se entra
        if(book.getId() != null){
            log.warn("trying to create a book with id");
            System.out.println("trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primary
    }



    //actualizar un libro  existente en base de datos
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId() == null){ //si no tiene id quiere decir que si es una creacion
            log.warn("Trying to update a non existent book no id provided ");
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.badRequest().build(); //400 mala peticion
        }
        if(!bookRepository.existsById(book.getId())){ //si no tiene id quiere decir que si es una creacion
            log.warn("Trying to update a non existent book no id provided, the book no exist"); //404 not found
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.notFound().build();
        }

        //el proceso de actualizacion
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    // borrar un libro en base de datos
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){

        if(!bookRepository.existsById(id)){ //si no tiene id quiere decir que si es una creacion
            log.warn("Trying to delete a non existent book no id provided, the book no exist"); //404 not found
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //se borro correctamente tipo 200 para adelante
    }

    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("Request Deleting all books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();

    }

}
