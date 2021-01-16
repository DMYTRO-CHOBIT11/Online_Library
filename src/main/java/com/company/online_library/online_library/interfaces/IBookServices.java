package com.company.online_library.online_library.interfaces;

import com.company.online_library.online_library.damain.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface IBookServices {
    Book createBook(Book book);
    Optional<Book> findById(long id);
    Book updateBookById(Book book,long id);
    void deleteBookById(long id);
    List<Book> findByNameContainingIgnoreCase(String value);
    List<Book> findAllBooks();
    Page<Book> findAllByGenre(int pageNo,int pageSize, long id);
    Page<Book>findAll(int pageNo,int pageSize);
    int countByNameContainingIgnoreCase(String value);
    File convertMultiPartFileToFile(MultipartFile multipartFile);
    List<Book> sortByParam(List<Book>bookList,String param);
}
