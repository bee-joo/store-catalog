package com.example.storecatalog.controller;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.service.StoreService;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.StoreView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    List<StoreView> getAllStores() {
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
    ResponseEntity<List<Address>> postStoreAddress(@PathVariable String id, @RequestBody Set<AddressDTO> addresses) {
        return storeService.addAddressToStore(id, addresses);
    }

    @PatchMapping({"/{id}"})
    ResponseEntity<Store> putStore(@PathVariable String id, @RequestBody StoreDTO storeDTO) {
        return storeService.updateStore(id, storeDTO);
    }

    @PatchMapping("/{storeId}/address/{addressId}")
    ResponseEntity<Address> putStoreAddress(@PathVariable String storeId,
                                            @PathVariable String addressId,
                                            @RequestBody AddressDTO addressDTO) {
        return storeService.updateStoreAddress(storeId, addressId, addressDTO);
    }

    @DeleteMapping("/{id}")
    HttpStatus deleteStore(@PathVariable String id) {
        return storeService.deleteStore(id);
    }
}
