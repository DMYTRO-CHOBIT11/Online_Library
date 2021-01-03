package com.company.online_library.online_library.damain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Введіть назву жанру")
    @Column(unique = true)
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},orphanRemoval = true)
    private Set<Book> books=new HashSet<>();

    public void removeGenreFromBooks(){
        for (Book b:books) {
            b.removeGenre(this);
        }
    }
    @Override
    public String toString() {
        return name;
    }
}
