package com.company.online_library.online_library.damain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.TreeSet;

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
    private String name;
    @OneToMany(mappedBy = "genre")
    private Set<Book> books=new TreeSet<>();

    @Override
    public String toString() {
        return name;
    }
}
