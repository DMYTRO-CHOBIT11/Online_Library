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
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введіть назву видавництва")
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books=new HashSet<>();

    @Override
    public String toString() {
        return name;
    }
}
