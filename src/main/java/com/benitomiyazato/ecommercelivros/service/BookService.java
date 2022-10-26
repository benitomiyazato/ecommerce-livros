package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import com.benitomiyazato.ecommercelivros.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> fetchBookList() {
        return bookRepository.findAll();
    }

    public List<Book> fetchBookListOfAuthorById(Long id) {
        return bookRepository.findByAuthorAuthorId(id);
    }

    public List<Book> fetchBookListOfGender(Gender gender) {
        return bookRepository.findByGendersContains(gender);
    }

    public List<Book> fetchBookListOfAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> findBookByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }
}
