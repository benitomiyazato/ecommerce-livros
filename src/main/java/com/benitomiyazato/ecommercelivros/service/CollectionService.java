package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Collection;
import com.benitomiyazato.ecommercelivros.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    public List<Collection> fetchCollectionList() {
        return collectionRepository.findAll();
    }

    public Collection saveNewCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public boolean existsByTitle(String title) {
        return collectionRepository.existsByTitle(title);
    }
}
