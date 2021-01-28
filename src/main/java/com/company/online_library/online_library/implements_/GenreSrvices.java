package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.interfaces.IGenreServices;
import com.company.online_library.online_library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Genre> findAll() {
        List<Genre>genres=repository.findAll();
        genres=genres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
        return genres;
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
        if(genre.getBooks().isEmpty()){
            repository.delete(genre);
        }else System.out.println("!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public Genre updateGenreById(Genre genre, long id) {
        Optional<Genre> newGenre=findById(id);
        newGenre.get().setName(genre.getName());
        return repository.save(newGenre.get());
    }
}
