package com.exercise.api.data.api.controllers;

import com.exercise.api.data.domain.Course;
import com.exercise.api.data.domain.Student;
import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.helpers.ConstantURL;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseControllerTest extends ControllerTest {

    private Teacher buildTeacher(String name) {
        return Teacher.builder()
                .name(name)
                .build();
    }

    private Course buildCourse(String name, String description) {
        return Course.builder()
                .name(name)
                .description(description)
                .build();
    }

    private Student buildStudent(String name) {
        return Student.builder()
                .name(name)
                .build();
    }

    private Student postStudent(Student student, URI uri) {
        HttpEntity<Student> request = new HttpEntity<>(student);
        return restTemplateAuth().postForObject(uri, request, Student.class);
    }

    private Teacher postTeacher(Teacher teacher, URI uri) {
        HttpEntity<Teacher> request = new HttpEntity<>(teacher);
        return restTemplateAuth().postForObject(uri, request, Teacher.class);
    }

    private Course postCourse(Course course, URI uri) {
        HttpEntity<Course> request = new HttpEntity<>(course);
        return restTemplateAuth().postForObject(uri, request, Course.class);
    }

    private URI buildCourseURI(Long teacherId, Long courseId) throws URISyntaxException {
        Map template = new HashMap<String, String>();
        template.put("teacher_id", teacherId.toString());
        if (Objects.isNull(courseId))
            return buildURI(template, ConstantURL.COURSES_BY_TEACHER);
        else {
            return buildURI(template, ConstantURL.COURSES_BY_TEACHER, courseId.toString());
        }
    }

    private URI buildCourseRegistrationURI(Long courseId, Long studentId) throws URISyntaxException {
        Map template = new HashMap<String, String>();
        template.put("id", courseId.toString());
        template.put("student_id", studentId.toString());
        return buildURI(template, ConstantURL.COURSE + "/{id}" + ConstantURL.COURSE_REGISTRATION);
    }

    @Test
    void getCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");
        Course course = buildCourse("post course name", "post course description");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);

        URI uri = buildURI(ConstantURL.COURSE, postedCourseResponse.getId().toString());
        Course gotCourse = restTemplateAuth().getForObject(uri, Course.class);

        assertThat(gotCourse).isNotNull();
        assertThat(gotCourse.getName()).isEqualTo(course.getName());
    }

    @Test
    void getCoursesSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("get test case");
        Course course1 = buildCourse("get all test case 1","descr1");
        Course course2 = buildCourse("get all test case 2", "descr2");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);

        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse1 = postCourse(course1, uriCourse);
        Course postedCourseResponse2 = postCourse(course2, uriCourse);

        URI uri = buildURI(ConstantURL.COURSE);
        ResponseEntity<Course[]> courseResponse = restTemplateAuth().getForEntity(uri, Course[].class);
        List courseList = Arrays.asList(courseResponse.getBody());

        assertThat(courseResponse).isNotNull();
        assertThat(courseList.size()).isGreaterThanOrEqualTo(2);
        assertThat(courseList).extracting("name")
                              .contains(postedCourseResponse1.getName(), postedCourseResponse2.getName());
    }

    @Test
    void registerStudentInCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");
        Course course = buildCourse("post course name", "post course description");
        Student student = buildStudent("get test case");

        URI uriStudent = buildURI(ConstantURL.STUDENTS);
        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);
        Student postedStudentResponse = postStudent(student, uriStudent);

        URI uri = buildCourseRegistrationURI(postedCourseResponse.getId(), postedStudentResponse.getId());

        ResponseEntity <String> response = restTemplateAuth().postForEntity(uri, null, String.class);


        assertThat(postedTeacherResponse).isNotNull();
        assertThat(postedCourseResponse).isNotNull();
        assertThat(postedStudentResponse).isNotNull();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("{\"status\":\"OK\"}");
    }

    @Test
    void unregisterStudentInCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");
        Course course = buildCourse("post course name", "post course description");
        Student student = buildStudent("get test case");

        URI uriStudent = buildURI(ConstantURL.STUDENTS);
        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);
        Student postedStudentResponse = postStudent(student, uriStudent);
        URI uri = buildCourseRegistrationURI(postedCourseResponse.getId(), postedStudentResponse.getId());
        ResponseEntity <String> responseRegistration = restTemplateAuth().postForEntity(uri, null, String.class);

        ResponseEntity <String> responseUnregistration = restTemplateAuth().exchange(uri, HttpMethod.DELETE, null, String.class);
        
        assertThat(postedTeacherResponse).isNotNull();
        assertThat(postedCourseResponse).isNotNull();
        assertThat(postedStudentResponse).isNotNull();
        assertThat(responseRegistration.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseUnregistration.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseUnregistration.getBody()).isEqualTo("{\"status\":\"OK\"}");
    }
}
