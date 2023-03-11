package com.miura.booksapi.controllers;

import com.miura.booksapi.models.Book;
import com.miura.booksapi.models.CreateBookRequest;
import com.miura.booksapi.models.UpdateBookRequest;
import com.miura.booksapi.repositories.BooksRepository;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksRepository booksRepository;

    public BooksController(final BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
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
    public Book createBook (@RequestBody CreateBookRequest request){

        Book book = new Book(
                null,
                request.getTitle(),
                request.getDescription(),
                request.getAuthor()
        );

        return booksRepository.save(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable UUID id,
            @RequestBody UpdateBookRequest request
    ) {
        Optional<Book> existingBook = booksRepository.findById(id.toString());

        if (existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book bookToUpdate = existingBook.get();

        bookToUpdate.setAuthor(request.getAuthor());
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