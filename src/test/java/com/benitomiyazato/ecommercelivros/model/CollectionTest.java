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
        book1 = Book.builder().price(50.0).build();
        book2 = Book.builder().price(50.0).build();
        book3 = Book.builder().price(50.0).build();

        collection = Collection.builder()
                .title("Coleção de Livros 1")
                .price(64.123)
                .books(List.of(book1, book2, book3))
                .build();
    }

    @Test
    @DisplayName("Returns percentage of discount of the collection with only one decimal place.")
    public void getAmountOfDescountPercentage_returnsPercentageOfDiscount_whenReceivesCollectionPriceAndSomatoryOfBooksPrices() {
        double percentageOfDescount =  collection.getAmountOfDescountPercentage();
        System.out.println("percentageOfDescount = " + percentageOfDescount);

        assertEquals(57.3, percentageOfDescount);
    }

    @Test
    @DisplayName("Returns amount of discount of the collection with only one decimal place.")
    public void getAmountOfDescountInMoney_returnsPercentageOfDiscount_whenReceivesCollectionPriceAndSomatoryOfBooksPrices() {
        double amountOfDescount =  collection.getAmountOfDescountInMoney();
        System.out.println("amountOfDescount = " + amountOfDescount);

        assertEquals(85.9, amountOfDescount);
    }
}