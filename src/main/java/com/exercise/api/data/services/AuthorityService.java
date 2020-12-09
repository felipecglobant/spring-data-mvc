package com.exercise.api.data.services;

import com.exercise.api.data.domain.Authority;
import com.exercise.api.data.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService extends AbstractService<Authority, AuthorityRepository> {

    @Autowired
    public AuthorityService(AuthorityRepository repository){
        this.repository = repository;
    }
}
