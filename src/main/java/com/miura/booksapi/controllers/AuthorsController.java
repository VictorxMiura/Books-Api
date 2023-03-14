package com.miura.booksapi.controllers;

import com.miura.booksapi.models.Author;
import com.miura.booksapi.models.CreateAuthorRequest;
import com.miura.booksapi.repositories.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorRepository authorRepository;

    public AuthorsController(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    @PostMapping
    public Author createBook (
            @RequestBody CreateAuthorRequest request
    ) {
        Author author = new Author(
                null,
                request.getName(),
                Collections.emptyList()
        );
        return authorRepository.save(author);
    }

}
