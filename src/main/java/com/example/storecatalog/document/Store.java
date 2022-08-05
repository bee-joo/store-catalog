package com.example.storecatalog.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Store {
    @Id
    private String id;

    private String name;
    private String description;

    private Set<String> addresses;
}
