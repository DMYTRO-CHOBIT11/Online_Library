package com.company.online_library.online_library.repositories;

import com.company.online_library.online_library.damain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Long> {
    List<Genre>findByNameContainingIgnoreCase(String genre);
}
