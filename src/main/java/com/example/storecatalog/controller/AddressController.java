package com.example.storecatalog.controller;

import com.example.storecatalog.service.AddressService;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.ResponseView;
import com.example.storecatalog.view.StoreView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    ResponseView<AddressView> getAddress(@PathVariable String id) {
        return addressService.findAddressById(id);
    }

    @GetMapping("/{id}/store")
    ResponseView<StoreView> getStoreByAddressId(@PathVariable String id) {
        return addressService.findStoreByAddressId(id);
    }
}
