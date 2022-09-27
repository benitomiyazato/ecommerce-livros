package com.benitomiyazato.ecommercelivros.repository;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private Book bookToSave;
    private Author author;
    @BeforeEach
    void setUp(){
        bookToSave = Book.builder()
                .title("Book One")
                .price(29.90)
                .description("É um bom livro!")
                .quantityInStock(5)
                .build();

        author = Author.builder()
                .name("Benito Miyazato")
                .biography("Uma longa vida")
                .build();
    }

    @Test
    @Transactional
    @DisplayName("Throws DataIntegrityViolationException when Book passed has null fields")
    public void saveBook_throwsDataIntegrityViolationException_whenBookPassedHasNullFields(){
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(bookToSave));
    }

    @Test
    @DisplayName("Creates new author when Book passed has non existing author in Database")
    public void saveBook_createsNewAuthor_whenBookPassedHasNonExistingAuthorInDataBase(){
        bookToSave.setAuthor(author);
        Book savedBook = bookRepository.save(bookToSave);

        assertEquals(author, savedBook.getAuthor());
        System.out.println("Author = " + savedBook.getAuthor());
    }

    @Test
    @DisplayName("Creates new genders when Book passed has non existing genders in Database")
    public void saveBook_createsNewGenders_whenBookPassedHasNonExistingGendersInDataBase(){
        Gender gender1 = Gender.builder()
                .description("Um gênero bom.")
                .name("Ação")
                .build();

        Gender gender2 = Gender.builder()
                .description("Um gênero ruim.")
                .name("Terror")
                .build();

        bookToSave.setAuthor(author);
        bookToSave.setGenders(List.of(gender1, gender2));

        Book savedBook = bookRepository.save(bookToSave);
        assertEquals(gender1, savedBook.getGenders().get(0));
        assertEquals(gender2, savedBook.getGenders().get(1));
        System.out.println("Genders = " + savedBook.getGenders());
    }
}