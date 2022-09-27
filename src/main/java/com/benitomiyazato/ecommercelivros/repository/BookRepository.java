package com.benitomiyazato.ecommercelivros.repository;

import com.benitomiyazato.ecommercelivros.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
