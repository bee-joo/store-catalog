package com.example.storecatalog.controller;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.service.StoreService;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.StoreView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    List<Store> getAllStores() {
        return storeService.findAllStores();
    }

    @GetMapping("/{id}")
    ResponseEntity<StoreView> getStore(@PathVariable String id) {
        return storeService.findStoreById(id);
    }

    @GetMapping("/{id}/address")
    List<AddressView> getAddressesByStoreId(@PathVariable String id) {
        return storeService.findAllAddressesById(id);
    }

    @PostMapping
    Store postStore(@RequestBody StoreDTO store) {
        return storeService.addStore(store);
    }

    @PostMapping("/{id}/address")
    ResponseEntity<List<Address>> postStoreAddress(@PathVariable String id, @RequestBody List<AddressDTO> addresses) {
        return storeService.addAddressToStore(id, addresses);
    }

    @DeleteMapping("/{id}")
    void deleteStore(@PathVariable String id) {
        storeService.deleteStore(id);
    }
}
