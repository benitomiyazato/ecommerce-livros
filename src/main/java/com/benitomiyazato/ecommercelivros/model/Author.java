package com.benitomiyazato.ecommercelivros.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "author")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Author {

    @Id
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long authorId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String biography;

    @Column(columnDefinition = "DATE")
    private LocalDate birthdate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    // returns the first three titles from this author
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
        Double averagePrice = books.stream().mapToDouble(Book::getPrice).average().orElse(0);
        String priceWithR$ = "R$" + averagePrice;
        return priceWithR$;
    }
}
