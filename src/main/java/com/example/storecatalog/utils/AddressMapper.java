package com.example.storecatalog.utils;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.exception.NotFoundException;
import com.example.storecatalog.exception.ValidationException;
import com.example.storecatalog.view.AddressView;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public static AddressView toView(Address entity) {
        AddressView addressView = new AddressView();

        addressView.setStreet(entity.getStreet());
        addressView.setCity(entity.getCity());
        addressView.setCountry(entity.getCountry());

        return addressView;
    }

    public static Address toEntity(AddressDTO dto) {
        Address address = new Address();

        if (dto.getStreet() == null || dto.getCountry() == null || dto.getCity() == null) {
            throw new ValidationException("Fields can't be null!");
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());

        return address;
    }

    public static Address updateEntity(AddressDTO dto, Address entity) {
        if (dto.getStreet() != null) entity.setStreet(dto.getStreet());
        if (dto.getCity() != null) entity.setCity(dto.getCity());
        if (dto.getCountry() != null) entity.setCountry(dto.getCountry());

        return entity;
    }
}
