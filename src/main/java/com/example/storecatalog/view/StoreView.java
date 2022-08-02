package com.example.storecatalog.view;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class StoreView {
    private String id, name, description;
    private List<AddressView> addresses;
}
