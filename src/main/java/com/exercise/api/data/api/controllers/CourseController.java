package com.exercise.api.data.api.controllers;

import com.exercise.api.data.domain.Course;
import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(ConstantURL.COURSE)
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public List<Course> courses(){
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourse(@PathVariable Long id){
        return courseService.get(id);
    }

    @PostMapping("/{id}" + ConstantURL.COURSE_REGISTRATION)
    public Map registerStudent(@PathVariable Long id, @PathVariable Long student_id){
        courseService.registerStudent(id, student_id);
        return Collections.singletonMap("status", "OK");
    }

    @DeleteMapping("/{id}" + ConstantURL.COURSE_REGISTRATION)
    public Map unregisterStudent(@PathVariable Long id, @PathVariable Long student_id){
        courseService.unregisterStudent(id, student_id);
        return Collections.singletonMap("status", "OK");
    }

}
