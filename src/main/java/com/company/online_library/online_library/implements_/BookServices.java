package com.company.online_library.online_library.implements_;


import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.interfaces.IBookServices;
import com.company.online_library.online_library.repositories.BookRepository;
import com.company.online_library.online_library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServices implements IBookServices {
    private final BookRepository repository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    public BookServices(BookRepository repository) {
        this.repository = repository;
    }

//    @Async(value = "taskExecutor")
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
        System.out.println(newBook.toString());
        System.out.println(book.toString());
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
    public String deleteBookById(long id) {
        Book book=repository.findById(id).get();
        book.removeAuthor(book.getAuthor());
        repository.delete(book);
        return "Book was deleted";
    }

    @Override
    public List<Book> findByNameContainingIgnoreCase(String value){
        List<Book>bookList=repository.findByNameContainingIgnoreCase(value);
        return bookList;
    }

    @Override
    public Iterable<Book> findAllBooks() {
       return repository.findAll();
    }

    @Override
    public int countBooks() {
        return repository.countBooks();
    }


    @Override
    public Page<Book>findAllByGenre(int pageNo,int pageSize,long id,String sortField) {
        Pageable pageable=PageRequest.of(pageNo-1,pageSize,Sort.by(sortField));
        Genre genre=genreRepository.findById(id).get();
        return repository.findAllByGenre(pageable,genre);
    }

    @Override
    public Page<Book> findAll(int pageNo,int pageSize,String sortField) {
        Sort sort=Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        return repository.findAll(pageable);
    }
//    @Override
//    public Page<Book> findAll(Pageable pageable) {
//        Integer pageNo= pageable.getPageNumber();
//        Integer pageSize=pageable.getPageSize();
//        pageable= PageRequest.of(pageNo-1,pageSize);
//        return repository.findAll(pageable);
//    }

    @Override
    public int countBooksByGenreId(Long id) {
        return repository.countBooksByGenreId(id);
    }

}
