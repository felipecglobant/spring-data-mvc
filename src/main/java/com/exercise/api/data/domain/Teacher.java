package com.exercise.api.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Course> courses;

    public Teacher() {
        courses = Collections.emptyList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Long id){
        courses.removeIf(course -> course.getId() == id);
    }

    @JsonIgnore
    public String getCoursesNames(){
        return courses.stream()
                      .map(Course::getName)
                      .collect(Collectors.joining(", "));
    }
}
