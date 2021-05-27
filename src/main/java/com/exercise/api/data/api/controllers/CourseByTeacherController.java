package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Course;;
import com.exercise.api.data.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantURL.COURSES_BY_TEACHER)
public class CourseByTeacherController {

    private final CourseService courseService;

    @Autowired
    public CourseByTeacherController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public List<Course> courses(@PathVariable Long teacher_id){
        return courseService.getAllByTeacher(teacher_id);
    }

    @PostMapping("")
    public Optional<Course> createCourse(@PathVariable Long teacher_id, @Validated @RequestBody Course course){
        return courseService.persistByTeacher(course, teacher_id);
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourse(@PathVariable Long teacher_id, @PathVariable Long id){
        return courseService.getByTeacher(id, teacher_id);
    }

    @PutMapping("/{id}")
    public Optional<Course> updateCourse(@RequestBody Course course, @PathVariable Long teacher_id, @PathVariable Long id){
        course.setId(id);
        return courseService.persistByTeacher(course, teacher_id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long teacher_id, @PathVariable Long id){
        courseService.removeByTeacher(id, teacher_id);
    }
}
