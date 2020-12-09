package com.exercise.api.data.services;

import com.exercise.api.data.domain.Student;
import com.exercise.api.data.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends AbstractService<Student, StudentRepository>{

    @Autowired
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }
}
