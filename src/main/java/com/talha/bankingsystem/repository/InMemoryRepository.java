package com.talha.bankingsystem.repository;

import com.talha.bankingsystem.model.Account;
import com.talha.bankingsystem.model.ModelClass;

import java.util.*;

public class InMemoryRepository<T extends ModelClass> implements Repository<T> {
    private Map<String, T> storage = new HashMap<>();

    @Override
    public void save(T entity) {
        String id = entity.getId();
        if (id != null) {
            storage.put(id, entity);
        }
        return;
    }

    @Override
    public void update(T entity) {
        if (storage.containsKey(entity.getId())) {
            storage.replace(entity.getId(), entity);
        }
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<T> findById(String id) {
        storage.get(id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

}
