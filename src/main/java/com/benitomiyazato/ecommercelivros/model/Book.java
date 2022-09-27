package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "book")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long bookId;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantityInStock;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "authorId", nullable = false)
    private Author author;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_gender_map",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name="gender_id", referencedColumnName = "genderId"))
    private List<Gender> genders;
}
