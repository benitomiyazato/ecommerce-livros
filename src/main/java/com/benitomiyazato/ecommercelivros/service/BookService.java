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
}
