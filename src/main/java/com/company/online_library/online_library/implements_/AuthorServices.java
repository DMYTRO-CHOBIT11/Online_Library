package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Author;
import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.interfaces.IAuthorServices;
import com.company.online_library.online_library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServices implements IAuthorServices {
    private  AuthorRepository repository;

    @Autowired
    public AuthorServices(AuthorRepository repository){
        this.repository=repository;
    }
    @Override
    public Author createAuthor(Author author) {
        return repository.save(author);
    }

    @Override
    public Optional<Author> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Author updateAuthorById(Author author, long id) {
        Author updateAuthor=repository.findById(id).get();
        updateAuthor.setFio(author.getFio());
        updateAuthor.setBirthday(author.getBirthday());
        return repository.save(updateAuthor);
    }

    @Override
    public String deleteAuthorById(long id) {
        Author author=repository.findById(id).get();
        for(Book b :author.getBookList()){
            author.getBookList().remove(b);
            b.getAuthor().remove(author);
        }
        repository.delete(author);
        return "Author was deleted";
    }

    @Override
    public Iterable<Author> findAllAuthors() {
        Iterable<Author>list=repository.findAll();
        return list;
    }
}
