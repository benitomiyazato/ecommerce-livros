package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> fetchAuthorList() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public void saveNewAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
