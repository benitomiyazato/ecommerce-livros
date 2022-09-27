package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "author")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Author {

    @Id
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long authorId;

    private String name;
    private String biography;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;
}
