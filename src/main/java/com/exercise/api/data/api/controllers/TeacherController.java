package com.exercise.api.data.api.controllers;

import static com.exercise.api.data.helpers.BatchStrategy.SEQUENTIAL;

import com.exercise.api.data.domain.TeacherList;
import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.services.TeacherService;
import java.util.Objects;
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

    @PostMapping("/batch")
    public Optional<TeacherList> createTeachersList(@RequestBody TeacherList teachers, @RequestParam(required = false) String strategy){
        if (Objects.isNull(strategy))
            strategy = SEQUENTIAL;
        return teacherService.createBatch(teachers, strategy);
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getTeacher(@PathVariable Long id){
        return teacherService.get(id);
    }

    @PutMapping("/{id}")
    public Optional<Teacher> updateTeacher(@RequestBody Teacher teacher, @PathVariable Long id){
        teacher.setId(id);
        return teacherService.update(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id){
        teacherService.delete(id);
    }
}
