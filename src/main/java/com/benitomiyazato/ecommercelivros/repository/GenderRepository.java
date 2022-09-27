package com.benitomiyazato.ecommercelivros.repository;

import com.benitomiyazato.ecommercelivros.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
}
