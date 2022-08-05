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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<StoreView> findAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(it -> storeMapper.toView(it))
                .toList();
    }

    public List<AddressView> findAllAddressesById(String id) {
        return addressRepository.findAddressesByStoreId(id)
                .stream()
                .map(it -> addressMapper.toView(it))
                .toList();
    }

    public ResponseEntity<List<Address>> addAddressToStore(String id, Set<AddressDTO> addresses) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent()) {
            Set<Address> newAddresses = addresses
                    .stream()
                    .map(it -> {
                        Address address = addressMapper.toEntity(it);
                        address.setStoreId(id);

                        return address;
                    })
                    .collect(Collectors.toSet());

            List<Address> storedAddresses = addressRepository.saveAll(newAddresses);
            Set<String> addressIds =
                    storedAddresses.stream()
                            .map(Address::getId)
                            .collect(Collectors.toSet());

            Store newStore = store.get();
            if (newStore.getAddresses() == null) {
                newStore.setAddresses(addressIds);
            } else {
                newStore.getAddresses().addAll(addressIds);
            }

            storeRepository.save(newStore);

            return new ResponseEntity<>(storedAddresses, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Store> updateStore(String id, StoreDTO storeDTO) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            Store storeEntity = storeMapper.toEntity(storeDTO, storeOptional.get());

            return new ResponseEntity<>(storeRepository.save(storeEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public HttpStatus deleteStore(String id) {
        addressRepository.deleteAddressesByStoreId(id);
        storeRepository.deleteById(id);

        return HttpStatus.OK;
    }

    public ResponseEntity<Address> updateStoreAddress(String storeId, String addressId, AddressDTO addressDTO) {
        Optional<Address> addressOptional = addressRepository.findAddressByIdAndStoreId(addressId, storeId);

        if (addressOptional.isPresent()) {
            Address addressEntity = addressMapper.toEntity(addressDTO, addressOptional.get());

            return new ResponseEntity<>(addressRepository.save(addressEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
