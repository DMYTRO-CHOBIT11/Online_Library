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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Введіть назву видавництва")
    private String name;

    @OneToMany(mappedBy = "publisher",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},
            orphanRemoval = true)
    private Set<Book> books=new HashSet<>();

    public void removePublisherFromBooks(){
        for (Book b:books) {
            b.removePublisher(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
