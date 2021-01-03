package com.company.online_library.online_library.repositories;


import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.damain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByNameContainingIgnoreCase(String value);
    @Query(nativeQuery = true,value = "select count(*)from book")
    int countBooks();
    int countBooksByGenreId(Long id);
    Page<Book> findAllByGenre(Pageable pageable, Genre genre);
}
