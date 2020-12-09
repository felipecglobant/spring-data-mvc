package com.exercise.api.data.services;

import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends AbstractService<Teacher, TeacherRepository> {

    @Autowired
    public TeacherService(TeacherRepository repository){
        this.repository = repository;
    }
}
