package com.example.storecatalog.service;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.exception.NotFoundException;
import com.example.storecatalog.repository.AddressRepository;
import com.example.storecatalog.repository.StoreRepository;
import com.example.storecatalog.utils.AddressMapper;
import com.example.storecatalog.utils.StoreMapper;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.ResponseView;
import com.example.storecatalog.view.StoreView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    public ResponseView<AddressView> findAddressById(String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Address with id %s not found", id)));

        return new ResponseView<>(AddressMapper.toView(address));
    }

    public ResponseView<StoreView> findStoreByAddressId(String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Address with id %s not found", id)));

        Store store = storeRepository.findById(address.getStoreId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Store with id %s not found", id)));

        return new ResponseView<>(StoreMapper.toView(store));
    }
}
