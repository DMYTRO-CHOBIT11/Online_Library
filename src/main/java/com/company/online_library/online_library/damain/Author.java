package com.company.online_library.online_library.damain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Введіть ім'я автора")
    private String fio;

    private Date birthday;

    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns =@JoinColumn(name = "author_id"),
            inverseJoinColumns =@JoinColumn(name = "book_id"))
    private Set<Book> bookList=new TreeSet<>();

    public Author() {
    }
}
