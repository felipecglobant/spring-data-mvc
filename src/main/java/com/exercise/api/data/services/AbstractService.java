package com.exercise.api.data.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
abstract class AbstractService<T, R extends CrudRepository<T, Long>> {

    protected R repository;

    public Optional<T> create(T object){
        return Optional.of(repository.save(object));
    }

    public Optional<T> get(long objectId){
        return repository.findById(objectId);
    }

    public List<T> getAll(){
        return (List<T>) repository.findAll();
    }

    public Optional<T> update(T object){
        return Optional.of(repository.save(object));
    }

    public void delete(Long objectId){
        repository.deleteById(objectId);
    }
}
