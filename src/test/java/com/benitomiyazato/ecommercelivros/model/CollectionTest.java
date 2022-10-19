package com.benitomiyazato.ecommercelivros.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollectionTest {

    Collection collection;

    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    void setUp() {
        book1 = Book.builder().title("Livro 1").price(50.0).build();
        book2 = Book.builder().title("Livro 2").price(50.0).build();
        book3 = Book.builder().title("Livro 3").price(50.0).build();

        collection = Collection.builder()
                .title("Coleção de Livros 1")
                .price(64.123)
                .books(List.of(book1, book2, book3))
                .build();
    }

    @Test
    @DisplayName("Returns percentage of discount of the collection with only one decimal place.")
    public void getAmountOfDiscountPercentage_returnsPercentageOfDiscount_whenReceivesCollectionPriceAndSumOfBooksPrices() {
        double percentageOfDiscount = collection.getAmountOfDiscountPercentage();
        System.out.println("percentageOfDiscount = " + percentageOfDiscount);

        assertEquals(57.3, percentageOfDiscount);
    }

    @Test
    @DisplayName("Returns amount of discount of the collection with only one decimal place.")
    public void getAmountOfDiscountInMoney_returnsPercentageOfDiscount_whenReceivesCollectionPriceAndSumOfBooksPrices() {
        double amountOfDiscount = collection.getAmountOfDiscountInMoney();
        System.out.println("amountOfDiscount = " + amountOfDiscount);

        assertEquals(85.9, amountOfDiscount);
    }

    @Test
    @DisplayName("Returns a string with the titles of the first three books of the collection")
    public void getBooksString_returnsStringOfTheTitlesOfFirstThreeBooksOfCollection() {
        String booksString = collection.getBooksString();
        System.out.println("booksString = " + booksString);
        assertEquals("Livro 1, Livro 2, Livro 3", booksString);
    }

    @Test
    @DisplayName("Returns a string with the titles of the first three books of the collection, has ... at the end if collection has more than 4 books")
    public void getBooksString_returnsStringOfTheTitlesOfFirstThreeBooksOfCollectionWithThreeDotsIfThereAreMoreThan3Books() {
        Book book4 = Book.builder().title("Livro 4").price(75.0).build();
        collection.setBooks(List.of(book1, book2, book3, book4));

        String booksString = collection.getBooksString();
        System.out.println("booksString = " + booksString);
        assertEquals("Livro 1, Livro 2, Livro 3...", booksString);
    }

    @Test
    @DisplayName("Returns a string with the titles of all books of the collection")
    public void getBooksStringFull_returnsStringOfTheTitlesOfAllBooksOfCollection() {
        String booksString = collection.getBooksStringFull();
        System.out.println("booksStringFull = " + booksString);
        assertEquals("Livro 1, Livro 2, Livro 3.", booksString);
    }
}