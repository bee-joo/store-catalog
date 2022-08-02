package com.example.storecatalog.repository;

import com.example.storecatalog.document.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store, String> {
}
