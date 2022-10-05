package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import com.benitomiyazato.ecommercelivros.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
