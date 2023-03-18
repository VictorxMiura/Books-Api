package com.miura.booksapi.controllers;

import com.miura.booksapi.models.*;
import com.miura.booksapi.repositories.AuthorRepository;
import com.miura.booksapi.repositories.BooksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    public BooksController(final BooksRepository booksRepository, final AuthorRepository authorRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookId (
            @PathVariable UUID id
            ) {
        Optional<Book> existingBook = booksRepository.findById(id.toString());

        if (existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(existingBook.get());
    }

    @PostMapping
    public ResponseEntity<Object> createBook (@RequestBody CreateBookRequest request){
        Optional<Author> existingAuthor = authorRepository.findById(request.getAuthorId());


        if (existingAuthor.isEmpty()) {
            return ResponseEntity.badRequest().body("AUTHOR NOT FOUND");
        }


        Book book = new Book(
                null,
                request.getTitle(),
                request.getDescription(),
                existingAuthor.get()
        );

        return ResponseEntity.ok(booksRepository.save(book));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable UUID id,
            @RequestBody UpdateBookRequest request
    ) {
        Optional<Book> existingBook = booksRepository.findById(id.toString());
        Optional<Author> existingAuthor = authorRepository.findById(request.getAuthorId());

        if (existingBook.isEmpty() || existingAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
       Book bookToUpdate = existingBook.get();
       Author authorToUpdate = existingAuthor.get();

        bookToUpdate.setAuthor(authorToUpdate);
        bookToUpdate.setDescription(request.getDescription());
        bookToUpdate.setTitle(request.getTitle());

        return ResponseEntity.ok(booksRepository.save(bookToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(
            @PathVariable UUID id
    )   {
        boolean existingBook = booksRepository.existsById(id.toString());

        if (!existingBook) {
            return ResponseEntity.notFound().build();
        }

        booksRepository.deleteById(id.toString());

        return ResponseEntity.noContent().build();

        }
}