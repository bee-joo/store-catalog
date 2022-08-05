package com.example.storecatalog.utils;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.view.AddressView;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressView, AddressDTO>{

    @Override
    public AddressView toView(Address entity) {
        AddressView addressView = new AddressView();

        addressView.setStreet(entity.getStreet());
        addressView.setCity(entity.getCity());
        addressView.setCountry(entity.getCountry());

        return addressView;
    }

    @Override
    public Address toEntity(AddressDTO dto) {
        Address address = new Address();

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());

        return address;
    }

    @Override
    public Address toEntity(AddressDTO dto, Address entity) {
        if (dto.getStreet() != null) entity.setStreet(dto.getStreet());
        if (dto.getCity() != null) entity.setCity(dto.getCity());
        if (dto.getCountry() != null) entity.setCountry(dto.getCountry());

        return entity;
    }
}
