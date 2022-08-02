package com.example.storecatalog.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreDTO {
    private String name, description;
    private List<AddressDTO> addresses;
}
