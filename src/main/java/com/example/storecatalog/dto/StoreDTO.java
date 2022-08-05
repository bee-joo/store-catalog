package com.example.storecatalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreDTO {
    private String id, name, description;
    private List<AddressDTO> addresses;
}
