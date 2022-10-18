package com.benitomiyazato.ecommercelivros.model;

import lombok.*;
import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

@Table(name = "collection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collection {

    @Id
    @SequenceGenerator(name = "collection_sequence", sequenceName = "collection_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_sequence")
    private Long collectionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int quantityInStock;

    private int soldUnits;

    private String fileName1;
    private String fileName2;
    private String fileName3;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "collection_book_map",
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"))
    private List<Book> books;

    public double getAmountOfDiscountPercentage() {
        DecimalFormat df = new DecimalFormat("#.##");
        double priceOfBooksIndividually = 0.0;
        for (Book book : books) {
            priceOfBooksIndividually += book.getPrice();
        }
        return Precision.round((1 - price / priceOfBooksIndividually) * 100, 1);
    }

    public String getAmountOfDiscountPercentageString() {
        double percentageOfDiscount = getAmountOfDiscountPercentage();
        return percentageOfDiscount > 0 ? percentageOfDiscount + "%" : "Não há desconto";
    }

    public double getAmountOfDiscountInMoney() {
        double priceOfBooksIndividually = 0.0;
        for (Book book : books) {
            priceOfBooksIndividually += book.getPrice();
        }
        return Precision.round(Math.abs(price - priceOfBooksIndividually), 1);
    }

    public String getBooksString() {
        System.out.println(books);
        int count = 0;
        String bookNames = "";

        for (Book book : books) {
            if (count > 3)
                break;

            String bookName = book.getTitle();
            if (count == 0) {
                bookNames += bookName;
            } else if (count == 3 && books.size() > 3) {
                bookNames += "...";
            } else {
                bookNames += ", " + bookName;
            }
            count++;
        }
        return bookNames;
    }
    public String getBooksStringFull() {
        System.out.println(books);
        int count = 0;
        String bookNames = "";

        for (Book book : books) {
            String bookName = book.getTitle();
            if (count == 0) {
                bookNames += bookName;
            } else if (count > 0 && count < books.size()){
                bookNames += ", " + bookName;
            } else {
                bookNames += ", " + bookName + ".";
            }
            count++;
        }
        return bookNames;
    }
}
