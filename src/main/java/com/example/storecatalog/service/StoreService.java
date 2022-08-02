package com.example.storecatalog.service;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.repository.AddressRepository;
import com.example.storecatalog.repository.StoreRepository;
import com.example.storecatalog.utils.AddressMapper;
import com.example.storecatalog.utils.StoreMapper;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.StoreView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {

    private StoreRepository storeRepository;
    private StoreMapper storeMapper;
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    public Store addStore(StoreDTO store) {
        Store newStore = storeMapper.toEntity(store);

        return storeRepository.save(newStore);
    }

    public ResponseEntity<StoreView> findStoreById(String id) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent()) {
            StoreView storeView = storeMapper.toView(store.get());
            return new ResponseEntity<>(storeView, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    public List<AddressView> findAllAddressesById(String id) {
        return addressRepository.findAddressesByStoreId(id)
                .stream()
                .map(it -> addressMapper.toView(it))
                .toList();
    }

    public ResponseEntity<List<Address>> addAddressToStore(String id, List<AddressDTO> addresses) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent()) {
            List<Address> newAddresses = addresses
                    .stream()
                    .map(it -> {
                        Address address = addressMapper.toEntity(it);
                        address.setStoreId(id);

                        return address;
                    })
                    .toList();

            List<Address> storedAddresses = addressRepository.saveAll(newAddresses);

            Store newStore = store.get();
            if (newStore.getAddresses() == null) {
                newStore.setAddresses(newAddresses);
            } else {
                newStore.getAddresses().addAll(storedAddresses);
            }

            storeRepository.save(newStore);

            return new ResponseEntity<>(storedAddresses, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteStore(String id) {
        addressRepository.deleteAddressesByStoreId(id);
        storeRepository.deleteById(id);
    }
}
