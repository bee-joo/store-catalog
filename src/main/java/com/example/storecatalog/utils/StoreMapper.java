package com.example.storecatalog.utils;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.repository.AddressRepository;
import com.example.storecatalog.view.StoreView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StoreMapper implements Mapper<Store, StoreView, StoreDTO>{

    private AddressMapper addressMapper;
    private AddressRepository addressRepository;

    @Override
    public StoreView toView(Store entity) {
        StoreView storeView = new StoreView();

        storeView.setId(entity.getId());
        storeView.setName(entity.getName());
        storeView.setDescription(entity.getDescription());

        if(entity.getAddresses() != null) {
            List<Address> addresses = (List<Address>) addressRepository.findAllById(entity.getAddresses());
            storeView.setAddresses(addresses
                    .stream()
                    .map(it -> addressMapper.toView(it))
                    .toList());
        }

        return storeView;
    }

    @Override
    public Store toEntity(StoreDTO dto) {
        Store store = new Store();

        store.setName(dto.getName());
        store.setDescription(dto.getDescription());

        return store;
    }

    @Override
    public Store toEntity(StoreDTO dto, Store entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());

        return entity;
    }
}
