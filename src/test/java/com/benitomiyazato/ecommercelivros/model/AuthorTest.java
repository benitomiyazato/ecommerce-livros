package com.benitomiyazato.ecommercelivros.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorTest {

    @Test
    @DisplayName("Returns first 3 titles from a specific author")
    public void getBookTitles_returnsFirstThreeTitlesFromSpecificAuthor(){
        Book book1 = Book.builder().title("Book 1").build();
        Book book2 = Book.builder().title("Book 2").build();
        Book book3 = Book.builder().title("Book 3").build();

        Author author = Author.builder().books(List.of(book1,book2,book3)).build();
        assertEquals("Book 1, Book 2, Book 3", author.getBookTitles());
        System.out.println(author.getBookTitles());
    }

    @Test
    @DisplayName("Returns first 3 titles from a specific author, if BookList's size > 3, has ... at the end")
    public void getBookTitles_returnsFirstThreeTitlesFromSpecificAuthorWithThreeDotsAtTheEndIfBookListIsGreaterThan3(){
        Book book1 = Book.builder().title("Book 1").build();
        Book book2 = Book.builder().title("Book 2").build();
        Book book3 = Book.builder().title("Book 3").build();
        Book book4 = Book.builder().title("Book 4").build();

        Author author = Author.builder().books(List.of(book1,book2,book3, book4)).build();
        assertEquals("Book 1, Book 2, Book 3...", author.getBookTitles());
        System.out.println(author.getBookTitles());
    }
}