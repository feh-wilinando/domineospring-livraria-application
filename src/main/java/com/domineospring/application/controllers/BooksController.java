package com.domineospring.application.controllers;

import com.domineospring.domain.models.Book;
import com.domineospring.domain.repository.BooksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BooksRepository repository;

    public BooksController(BooksRepository repository) {
        this.repository = repository;
    }


    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable Integer id){

        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


    @GetMapping
    public ResponseEntity<Iterable<Book>> getAll(){
        List<Book> all = repository.findAll();

        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(all);
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book){

        repository.save(book);

        URI location = URI.create("/books/" + book.getId());
        
        return ResponseEntity
                .created(location)
                .body(book);

    }


}
