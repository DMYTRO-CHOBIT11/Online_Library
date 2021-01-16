package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Author;
import com.company.online_library.online_library.interfaces.IAuthorServices;
import com.company.online_library.online_library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void deleteAuthorById(long id) {
        Author author = repository.findById(id).get();
        if (author.getBookList().isEmpty()) {
            repository.delete(author);
        }
    }

    @Override
    public List<Author> findAllAuthors() {
        List<Author>list=repository.findAll();
        list=list.stream().sorted(Comparator.comparing(Author::getFio)).collect(Collectors.toList());
        return list;
    }
}
