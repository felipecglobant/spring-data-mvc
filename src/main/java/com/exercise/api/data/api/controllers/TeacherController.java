package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantURL.TEACHERS)
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("")
    public List<Teacher> teachers(){
        return teacherService.getAll();
    }

    @PostMapping("")
    public Optional<Teacher> createTeachers(@RequestBody Teacher student){
        return teacherService.create(student);
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getTeacher(@PathVariable Long id){
        return teacherService.get(id);
    }

    @PutMapping("/{id}")
    public Optional<Teacher> updateTeacher(@RequestBody Teacher student, @PathVariable Long id){
        student.setId(id);
        return teacherService.update(student);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id){
        teacherService.delete(id);
    }
}
