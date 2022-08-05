package com.example.storecatalog.repository;

import com.example.storecatalog.document.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findAddressesByStoreId(String id);
    void deleteAddressesByStoreId(String id);

    Optional<Address> findAddressByIdAndStoreId(String id, String storeId);
}
