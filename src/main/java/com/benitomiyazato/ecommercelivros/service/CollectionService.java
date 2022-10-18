package com.benitomiyazato.ecommercelivros.service;

import com.benitomiyazato.ecommercelivros.model.Collection;
import com.benitomiyazato.ecommercelivros.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    public List<Collection> fetchCollectionList() {
        return collectionRepository.findAll();
    }

    @Transactional
    public Collection saveNewCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public boolean existsByTitle(String title) {
        return collectionRepository.existsByTitle(title);
    }

    public void deleteById(Long collectionId) {
        collectionRepository.deleteById(collectionId);
    }

    public void deleteCollectionById(Long id) {
        collectionRepository.deleteById(id);
    }

    public Optional<Collection> findCollectionById(Long id) {
        return collectionRepository.findById(id);
    }
}
