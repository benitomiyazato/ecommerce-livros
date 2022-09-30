package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "gender")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Gender {

    @Id
    @SequenceGenerator(name = "gender_sequence", sequenceName = "gender_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_sequence")
    private Long genderId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToMany(mappedBy = "genders", fetch = FetchType.LAZY)
    private List<Book> books;

    public String getBookTitles() {
        int count = 0;
        String bookTitles = "";

        for (Book book : books) {
            if (count > 3)
                break;

            String bookTitle = book.getTitle();
            if (count == 0) {
                bookTitles += bookTitle;
            } else if (count == 3 && books.size() > 3) {
                bookTitles += "...";
            } else {
                bookTitles += ", " + bookTitle;
            }
            count++;
        }
        return bookTitles;
    }

    public String getAverageBookPrice() {
        double averagePrice = books.stream().mapToDouble(Book::getPrice).average().orElse(0);
        return "R$" + averagePrice;
    }
}
