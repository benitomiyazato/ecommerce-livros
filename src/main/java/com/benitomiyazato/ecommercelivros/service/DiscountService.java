package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Discount;
import com.benitomiyazato.ecommercelivros.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public Discount saveNewDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public List<Discount> fetchAllDiscounts() {
        return discountRepository.findAll();
    }

    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    public void delete(Discount discount) {
        discountRepository.delete(discount);
    }
}
