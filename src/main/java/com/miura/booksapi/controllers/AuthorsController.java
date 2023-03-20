package com.miura.booksapi.controllers;

import com.miura.booksapi.models.Author;
import com.miura.booksapi.models.CreateAuthorRequest;
import com.miura.booksapi.models.UpdateAuthorRequest;
import com.miura.booksapi.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor (
            @PathVariable UUID id,
            @RequestBody UpdateAuthorRequest arequest
            ) {
        Optional<Author> existingAuthor = authorRepository.findById(id.toString());

        if (existingAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

       Author authorToUpdate = existingAuthor.get();

        authorToUpdate.setName(arequest.getName());

        return ResponseEntity.ok(authorRepository.save(authorToUpdate));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAuthor (
            @PathVariable UUID id
    ) {
        boolean existingAuthor = authorRepository.existsById(id.toString());

        if (!existingAuthor) {
            return ResponseEntity.notFound().build();
        }

        authorRepository.deleteById(id.toString());

        return ResponseEntity.noContent().build();
    }

}
