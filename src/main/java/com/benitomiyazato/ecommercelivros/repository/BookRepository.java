package com.benitomiyazato.ecommercelivros.repository;

import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByAuthorAuthorId(Long id);

    public List<Book> findByGendersContains(Gender gender);

}
