package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "book")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long bookId;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int quantityInStock;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "authorId", nullable = false)
    private Author author;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_gender_map",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "gender_id", referencedColumnName = "genderId"))
    private List<Gender> genders;

    @Column(columnDefinition = "DATE")
    private LocalDate publicationDate;

    private int soldUnits;

    private String image1Path;
    private String image2Path;
    private String image3Path;

    public String getGendersString() {
        int count = 0;
        String genderNames = "";

        for (Gender gender : genders) {
            if (count > 3)
                break;

            String genderName = gender.getName();
            if (count == 0) {
                genderNames += genderName;
            } else if (count == 3 && genders.size() > 3) {
                genderNames += "...";
            } else {
                genderNames += ", " + genderName;
            }
            count++;
        }
        return genderNames;
    }
}
