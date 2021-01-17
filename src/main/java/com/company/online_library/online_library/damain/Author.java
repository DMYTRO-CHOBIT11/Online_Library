package com.company.online_library.online_library.damain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Введіть ім'я автора")
    private String fio;

    private Date birthday;

    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns =@JoinColumn(name = "author_id"),
            inverseJoinColumns =@JoinColumn(name = "book_id"))
    private Set<Book> bookList=new HashSet<>();

    public Author() {
    }
}
