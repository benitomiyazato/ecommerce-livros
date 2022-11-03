package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Discount;
import com.benitomiyazato.ecommercelivros.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public void saveNewDiscount(Discount discount) {
        discountRepository.save(discount);
    }

    public List<Discount> fetchAllDiscounts() {
        return discountRepository.findAll();
    }
}
