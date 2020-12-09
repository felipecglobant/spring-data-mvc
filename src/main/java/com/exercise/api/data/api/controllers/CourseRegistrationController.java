package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Course;
import com.exercise.api.data.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(ConstantURL.COURSE)
public class CourseRegistrationController {

    private final CourseService courseService;

    @Autowired
    public CourseRegistrationController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public Optional<Course> course(@PathVariable Long course_id){
        return courseService.get(course_id);
    }


    @PostMapping(ConstantURL.COURSE_REGISTRATION)
    public Map registerStudent(@PathVariable Long course_id, @PathVariable Long student_id){
        courseService.registerStudent(course_id, student_id);
        return Collections.singletonMap("status", "OK");
    }

    @DeleteMapping(ConstantURL.COURSE_REGISTRATION)
    public Map unregisterStudent(@PathVariable Long course_id, @PathVariable Long student_id){
        courseService.unregisterStudent(course_id, student_id);
        return Collections.singletonMap("status", "OK");
    }

}
