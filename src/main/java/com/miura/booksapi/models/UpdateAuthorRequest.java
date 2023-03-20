package com.miura.booksapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorRequest {
    private String authorId;

    private String name;

    private List<Book> books;
}
