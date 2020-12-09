package com.exercise.api.data.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    private String name;

    @ManyToMany
    private Set<Course> courses;

    public Student(){
    }

    public Student(Long id, String name) {
        Id = id;
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
