package com.example.demo.service;

import java.util.List;

public interface CRUDService<T, Long> {
    List<T> getAll();

    void save(T t);

    T getById(Long id);

    void deleteById(Long id);
}
