package com.company.online_library.online_library.interfaces;

import com.company.online_library.online_library.damain.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IBookServices {
    Book createBook(Book book);
    Optional<Book> findById(long id);
    Book updateBookById(Book book,long id);
    String deleteBookById(long id);
    List<Book> findByNameContainingIgnoreCase(String value);
    Iterable<Book> findAllBooks();
    int countBooks();
    Page<Book> findAllByGenre(int pageNo,int pageSize, long id, String SortField);
    Page<Book>findAll(int pageNo,int pageSize,String sortField);
    int countBooksByGenreId(Long id);
    int countByNameContainingIgnoreCase(String value);

}
