package com.example.storecatalog.service;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private StoreRepository storeRepository;
    private AddressMapper addressMapper;
    private StoreMapper storeMapper;

    public ResponseEntity<AddressView> findAddressById(String id) {
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()) {
            AddressView addressView = addressMapper.toView(address.get());
            return new ResponseEntity<>(addressView, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<StoreView> findStoreByAddressId(String id) {
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()) {
            Store store = storeRepository.findById(address.get().getStoreId()).get();
            return new ResponseEntity<>(storeMapper.toView(store), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
