package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.interfaces.IGenreServices;
import com.company.online_library.online_library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreSrvices implements IGenreServices {
    @Autowired
    private GenreRepository repository;
    @Override
    public List<Genre>findByNameContainingIgnoreCase(String genre){
        List<Genre>genreList=repository.findByNameContainingIgnoreCase(genre);
        return genreList;
    }

    @Override
    public Iterable<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public Genre addGenre(Genre genre) {
        return repository.save(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteGenreById(long id) {
        Genre genre=repository.findById(id).get();
        genre.removeGenreFromBooks();
        repository.delete(genre);
    }

    @Override
    public Genre updateGenreById(Genre genre, long id) {
        Optional<Genre> newGenre=findById(id);
        newGenre.get().setName(genre.getName());
        return repository.save(newGenre.get());
    }

}
