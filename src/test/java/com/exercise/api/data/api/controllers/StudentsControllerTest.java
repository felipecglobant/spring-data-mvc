package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentsControllerTest extends ControllerTest {

    private Student buildStudent(String name) {
        return Student.builder()
                      .name(name)
                      .build();
    }

    private Student postStudent(Student student, URI uri) {
        HttpEntity<Student> request = new HttpEntity<>(student);
        return restTemplate.postForObject(uri, request, Student.class);
    }

    @Test
    void createStudentSuccessfully() throws URISyntaxException {
        Student student = buildStudent("post test case");

        URI uri = buildURI(ConstantURL.STUDENTS);
        Student postedStudentResponse = postStudent(student, uri);

        assertThat(postedStudentResponse).isNotNull();
        assertThat(postedStudentResponse.getName()).isEqualTo(student.getName());
    }

    @Test
    void getStudentSuccessfully() throws URISyntaxException {
        Student student = buildStudent("get test case");

        URI uri = buildURI(ConstantURL.STUDENTS);
        Student postedStudentResponse = postStudent(student, uri);

        URI entityUri = buildURI(ConstantURL.STUDENTS, postedStudentResponse.getId()
                .toString()
        );
        Student studentResponse = restTemplate.getForObject(entityUri, Student.class);

        assertThat(studentResponse).isNotNull();
        assertThat(studentResponse.getName()).isEqualTo(student.getName());

    }

    @Test
    void getStudentsSuccessfully() throws URISyntaxException {
        Student student1 = buildStudent("get all test case 1");
        Student student2 = buildStudent("get all test case 2");

        URI uri = buildURI(ConstantURL.STUDENTS);
        Student postedStudentResponse1 = postStudent(student1, uri);
        Student postedStudentResponse2 = postStudent(student2, uri);

        ResponseEntity<Student[]> StudentResponse = restTemplate.getForEntity(uri, Student[].class);
        List StudentList = Arrays.asList(StudentResponse.getBody());

        assertThat(StudentResponse).isNotNull();
        assertThat(StudentList.size()).isGreaterThanOrEqualTo(2);
        assertThat(StudentList).extracting("name")
                .contains(student1.getName(), student2.getName());
    }

    @Test
    void deleteStudentSuccessfully() throws URISyntaxException {
        Student student = buildStudent("delete test case");

        URI uri = buildURI(ConstantURL.STUDENTS);
        Student postedStudentResponse = postStudent(student, uri);

        URI entityUri = buildURI(ConstantURL.STUDENTS, postedStudentResponse.getId()
                .toString()
        );
        Student studentResponse = restTemplate.getForObject(entityUri, Student.class);

        assertThat(studentResponse).isNotNull();
        assertThat(studentResponse.getName()).isEqualTo(student.getName());

        restTemplate.delete(entityUri);
        studentResponse = restTemplate.getForObject(entityUri, Student.class);

        assertThat(studentResponse).isNull();
    }

}
