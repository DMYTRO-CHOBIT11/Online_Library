package com.company.online_library.online_library.damain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long book_id;

    @NotBlank(message = "Введіть назву книги")
    private String name;

    @Column(unique = true)
    private int isbn;

    private String content;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotNull(message = "Вкажіть автора книги")
    @ManyToMany(mappedBy = "bookList")
    private Set<Author> author=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "publish_year")
    private int publishYear;

    private String image;

    private String descr;

    public void addAuthor(Set<Author> addAuthors) {
        for (Author a: addAuthors) {
            a.getBookList().add(this);
            author.add(a);
        }
    }

    public void removeAuthor(Set<Author> removeAuthors) {
        for (Author a: removeAuthors) {
            a.getBookList().remove(this);
            author.remove(a);
        }
    }

    public Book(@NotBlank(message = "Введіть назву книги") String name, int isbn, Genre genre, @NotNull(message = "Вкажіть автора книги") Set<Author> author, Publisher publisher, int publishYear, String descr) {
        this.name = name;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", name='" + name + '\'' +
                ", isbn=" + isbn +
                ", content='" + content + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                ", publisher=" + publisher +
                ", publishYear=" + publishYear +
                ", image='" + image + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }
}

