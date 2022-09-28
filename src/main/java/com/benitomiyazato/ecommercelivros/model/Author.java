package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
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
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long authorId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String biography;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    // returns the first three titles from this author
    public String getBookTitles() {
        int count = 0;
        String bookTitles = "";

        for (Book book : books) {
            if(count > 3)
                break;

            String bookTitle = book.getTitle();
            if (count == 0) {
                bookTitles += bookTitle;
            } else if (count == 3 && books.size() > 3){
                bookTitles += "...";
            }else {
                bookTitles += ", " + bookTitle;
            }
            count++;
        }
        return bookTitles;
    }
}
