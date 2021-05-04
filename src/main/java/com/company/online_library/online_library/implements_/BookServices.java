package com.company.online_library.online_library.implements_;

import com.amazonaws.services.s3.AmazonS3;
import com.company.online_library.online_library.domain.Book;
import com.company.online_library.online_library.interfaces.IBookServices;
import com.company.online_library.online_library.repositories.BookRepository;
import com.company.online_library.online_library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookServices implements IBookServices {
    private final BookRepository repository;
    private GenreRepository genreRepository;
    private AmazonS3 s3client;

    @Autowired
    public BookServices(BookRepository repository,GenreRepository genreRepository,AmazonS3 s3client) {
        this.repository = repository;
        this.s3client=s3client;
        this.genreRepository=genreRepository;
    }

    @Override
    public Book createBook(Book book) {
        Book newBook=new Book();
        newBook.setImage(book.getImage());
        newBook.setGenre(book.getGenre());
        newBook.setPublisher(book.getPublisher());
        newBook.setName(book.getName());
        newBook.setContent(book.getContent());
        newBook.setIsbn(book.getIsbn());
        newBook.setPublishYear(book.getPublishYear());
        newBook.setDescr(book.getDescr());
        newBook.setImage(book.getImage());
        newBook.setContent(book.getContent());
        newBook.addAuthor(book.getAuthor());
        return repository.save(newBook);
    }

    @Override
    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Book updateBookById(Book book,long id) {
        Book newBook=repository.findById(id).get();
        newBook.setName(book.getName());
        newBook.setDescr(book.getDescr());
        newBook.setIsbn(book.getIsbn());
        newBook.setPublisher(book.getPublisher());
        newBook.setGenre(book.getGenre());
        newBook.setPublishYear(book.getPublishYear());
        newBook.removeAuthor(newBook.getAuthor());
        newBook.addAuthor(book.getAuthor());
        return repository.save(newBook);
    }

    @Override
    public void deleteBookById(long id) {
        Book book=repository.findById(id).get();
        book.removeAuthor(book.getAuthor());
        repository.delete(book);
    }

    @Override
    public List<Book> findByNameContainingIgnoreCase(String value){
        List<Book>bookList=repository.findByNameContainingIgnoreCase(value);
        return bookList;
    }

    @Override
    public List<Book> findAllBooks() {
       List<Book>books=repository.findAll();
        books=books.stream().sorted((b1, b2) -> {
            if (b1.getAuthor().iterator().next().getFio().equals(b2.getAuthor().iterator().next().getFio())){
                return b1.getName().compareTo(b2.getName());
            } else return b1.getAuthor().iterator().next().getFio().compareTo(b2.getAuthor().iterator().next().getFio()) ;
        }).collect(Collectors.toList());
       return books;
    }

    @Override
    public Page<Book>findAllByGenre(int pageNo,int pageSize,long id) {
        Pageable pageable=PageRequest.of(pageNo-1,pageSize);
        return repository.findAllByGenre(pageable,genreRepository.findById(id).get());
    }

    @Override
    public Page<Book> findAll(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return repository.findAll(pageable);
    }

    public List<Book> sortByParam(List<Book>bookList,String param){
        List<Book>sortedBook;
       if (param.equals("name")){
           sortedBook=bookList.stream().sorted(Comparator.comparing(Book::getName)).collect(Collectors.toList());
       }else{
           sortedBook=bookList.stream().sorted((b1, b2) -> {
               if (b1.getAuthor().iterator().next().getFio().equals(b2.getAuthor().iterator().next().getFio())){
                   return b1.getName().compareTo(b2.getName());
               } else return b1.getAuthor().iterator().next().getFio().compareTo(b2.getAuthor().iterator().next().getFio()) ;
           }).collect(Collectors.toList());
           return sortedBook;
       }
        return sortedBook;
    }

    @Override
    public int countByNameContainingIgnoreCase(String value) {
        return repository.countByNameContainingIgnoreCase(value);
    }

    public File convertMultiPartFileToFile(MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream outputStream = new FileOutputStream(file) ;
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return file;
    }
}