package com.example.storecatalog.controller;

import com.example.storecatalog.document.Address;
import com.example.storecatalog.document.Store;
import com.example.storecatalog.dto.AddressDTO;
import com.example.storecatalog.dto.StoreDTO;
import com.example.storecatalog.service.StoreService;
import com.example.storecatalog.view.AddressView;
import com.example.storecatalog.view.ResponseView;
import com.example.storecatalog.view.StoreView;
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
    ResponseView<List<StoreView>> getAllStores() {
        return storeService.findAllStores();
    }

    @GetMapping("/{id}")
    ResponseView<StoreView> getStore(@PathVariable String id) {
        return storeService.findStoreById(id);
    }

    @GetMapping("/{id}/address")
    ResponseView<List<AddressView>> getAddressesByStoreId(@PathVariable String id) {
        return storeService.findAllAddressesById(id);
    }

    @PostMapping
    ResponseView<Store> postStore(@RequestBody StoreDTO store) {
        return storeService.addStore(store);
    }

    @PostMapping("/{id}/address")
    ResponseView<List<Address>> postStoreAddress(@PathVariable String id, @RequestBody Set<AddressDTO> addresses) {
        return storeService.addAddressToStore(id, addresses);
    }

    @PatchMapping({"/{id}"})
    ResponseView<Store> putStore(@PathVariable String id, @RequestBody StoreDTO storeDTO) {
        return storeService.updateStore(id, storeDTO);
    }

    @PatchMapping("/{storeId}/address/{addressId}")
    ResponseView<Address> putStoreAddress(@PathVariable String storeId,
                                            @PathVariable String addressId,
                                            @RequestBody AddressDTO addressDTO) {
        return storeService.updateStoreAddress(storeId, addressId, addressDTO);
    }

    @DeleteMapping("/{id}")
    ResponseView<String> deleteStore(@PathVariable String id) {
        return storeService.deleteStore(id);
    }
}
