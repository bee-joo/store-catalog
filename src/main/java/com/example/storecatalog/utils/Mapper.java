package com.example.storecatalog.utils;

public interface Mapper<E, V, D> {
    V toView(E entity);
    E toEntity(D dto);

    E toEntity(D dto, E entity);
}
