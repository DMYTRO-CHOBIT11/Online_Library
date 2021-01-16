package com.company.online_library.online_library.interfaces;


import com.company.online_library.online_library.damain.Author;

import java.util.Optional;

public interface IAuthorServices {
    Author createAuthor(Author author);
    Optional<Author> findById(long id);
    Author updateAuthorById(Author author,long id);
    void deleteAuthorById(long id);
    Iterable<Author>findAllAuthors();
}

