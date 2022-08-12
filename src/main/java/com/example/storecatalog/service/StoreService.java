package com.example.storecatalog.service;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.dto.StoreDTO;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {

    private StoreRepository storeRepository;
    private AddressRepository addressRepository;

    public ResponseView<Store> addStore(StoreDTO store) {
        Store newStore = storeRepository.save(StoreMapper.toEntity(store));

        return new ResponseView<>(newStore);
    }

    public ResponseView<StoreView> findStoreById(String id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Store with id %s not found", id)));

        StoreView storeView = StoreMapper.toView(store);

        return new ResponseView<>(storeView);
    }

    public ResponseView<List<StoreView>> findAllStores() {
        List<StoreView> storeViews = storeRepository.findAll()
                .stream()
                .map(StoreMapper::toView)
                .toList();

        return new ResponseView<>(storeViews);
    }

    public ResponseView<List<AddressView>> findAllAddressesById(String id) {
        List<AddressView> addressViews = addressRepository.findAddressesByStoreId(id)
                .stream()
                .map(AddressMapper::toView)
                .toList();

        return new ResponseView<>(addressViews);
    }

    public ResponseView<List<Address>> addAddressToStore(String id, Set<AddressDTO> addresses) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Store with id %s not found", id)));

        Set<Address> newAddresses = addresses
                .stream()
                .map(it -> {
                    Address address = AddressMapper.toEntity(it);
                    address.setStoreId(id);

                    return address;
                })
                .collect(Collectors.toSet());

        List<Address> storedAddresses = addressRepository.saveAll(newAddresses);
        Set<String> addressIds = storedAddresses
                .stream()
                .map(Address::getId)
                .collect(Collectors.toSet());

        if (store.getAddresses() == null) {
            store.setAddresses(addressIds);
        } else {
            store.getAddresses().addAll(addressIds);
        }

        storeRepository.save(store);

        return new ResponseView<>(storedAddresses);
    }

    public ResponseView<Store> updateStore(String id, StoreDTO storeDTO) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Store with id %s not found", id)));

        Store storeEntity = StoreMapper.updateEntity(storeDTO, store);

        return new ResponseView<>(storeRepository.save(storeEntity));
    }

    public ResponseView<String> deleteStore(String id) {
        addressRepository.deleteAddressesByStoreId(id);
        storeRepository.deleteById(id);

        return new ResponseView<>("Deleted");
    }

    public ResponseView<Address> updateStoreAddress(String storeId, String addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findAddressByIdAndStoreId(addressId, storeId)
                .orElseThrow(() -> new NotFoundException("Not found"));

        Address addressEntity = AddressMapper.updateEntity(addressDTO, address);

        return new ResponseView<>(addressRepository.save(addressEntity));
    }
}
