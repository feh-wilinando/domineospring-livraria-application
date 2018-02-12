package com.domineospring.application.controllers;

import com.domineospring.domain.models.Author;
import com.domineospring.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorsController {


    private final AuthorRepository repository;

    @Autowired
    public AuthorsController(AuthorRepository repository) {
        this.repository = repository;
    }


    @GetMapping("{name}")
    public ResponseEntity<Author> getOne(@PathVariable String name){

        return repository
                .findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Author>> getAll(){
        List<Author> all = repository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(all);
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Author author){

        repository.save(author);

        URI location = URI.create("/authors/" + author.getName());

        return ResponseEntity
                .created(location).body(author);
    }
}
