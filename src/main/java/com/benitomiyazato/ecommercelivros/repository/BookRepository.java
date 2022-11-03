package com.benitomiyazato.ecommercelivros.repository;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Book;
import com.benitomiyazato.ecommercelivros.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByAuthorAuthorId(Long id);

    public List<Book> findByGendersContains(Gender gender);

    boolean existsByTitle(String title);

    Optional<Book> findByTitle(String bookTitle);

    @Query("select b from Book b where b.author = ?1")
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContainsIgnoreCase(String title);


    List<Book> findByAuthorNameContainsIgnoreCase(String keyword);

    List<Book> findByGenders_NameContainsIgnoreCase(String name);


}
