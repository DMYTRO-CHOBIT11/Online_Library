package com.company.online_library.online_library.interfaces;


import com.company.online_library.online_library.damain.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreServices {
    List<Genre>findByNameContainingIgnoreCase(String genre);
    List<Genre>findAll();
    Genre addGenre(Genre genre);
    Optional<Genre> findById(long id);
    void deleteGenreById(long id);
    Genre updateGenreById(Genre genre, long id);
}
