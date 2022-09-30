package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Gender;
import com.benitomiyazato.ecommercelivros.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<Gender> fetchGenderList() {
        return genderRepository.findAll();
    }

    public Gender saveNewGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public Optional<Gender> findGenderById(Long id) {
        return genderRepository.findById(id);
    }
}
