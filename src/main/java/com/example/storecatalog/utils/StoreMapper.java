package com.example.storecatalog.utils;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.exception.ValidationException;
import com.example.storecatalog.repository.AddressRepository;
import com.example.storecatalog.view.StoreView;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Setter
public class StoreMapper {

    private static AddressRepository addressRepository;

    public static StoreView toView(Store entity) {
        StoreView storeView = new StoreView();

        storeView.setId(entity.getId());
        storeView.setName(entity.getName());
        storeView.setDescription(entity.getDescription());

        if (entity.getAddresses() != null) {
            List<Address> addresses = (List<Address>) addressRepository.findAllById(entity.getAddresses());
            storeView.setAddresses(addresses
                    .stream()
                    .map(AddressMapper::toView)
                    .toList());
        }

        return storeView;
    }

    public static Store toEntity(StoreDTO dto) {
        Store store = new Store();

        if (dto.getName() == null || dto.getDescription() == null) {
            throw new ValidationException("Fields can't be null!");
        }

        store.setName(dto.getName());
        store.setDescription(dto.getDescription());

        return store;
    }


    public static Store updateEntity(StoreDTO dto, Store entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());

        return entity;
    }
}
