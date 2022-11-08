package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.text.NumberFormat;
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

    @Column(nullable = false, columnDefinition = "TEXT")
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
            inverseJoinColumns = @JoinColumn(name = "gender_id", referencedColumnName = "genderId")
    )
    private List<Gender> genders;

    @Column(nullable = true)
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Collection> collections;

    @OneToOne(mappedBy = "book")
    private Discount discount;

    @Column(columnDefinition = "DATE")
    private LocalDate publicationDate;

    private int soldUnits;

    private String fileName1;
    private String fileName2;
    private String fileName3;

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

    public String getGendersStringFull() {
        int count = 1;
        String genderNames = "";

        for (Gender gender : genders) {
            String genderName = gender.getName();
            if (count == 1) {
                genderNames += genderName;
            } else if (count == genders.size()) {
                genderNames += ", " + genderName + ".";
            } else {
                genderNames += ", " + genderName;
            }
            count++;
        }
        return genderNames;
    }

    public boolean atDiscount() {
        return discount != null;
    }
}
