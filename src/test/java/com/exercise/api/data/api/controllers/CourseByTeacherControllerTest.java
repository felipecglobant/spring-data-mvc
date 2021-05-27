package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Course;
import com.exercise.api.data.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseByTeacherControllerTest extends ControllerTest {

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

    private Teacher postTeacher(Teacher teacher, URI uri) {
        HttpEntity<Teacher> request = new HttpEntity<>(teacher);
        return restTemplateAuth().postForObject(uri, request, Teacher.class);
    }

    private Course postCourse(Course course, URI uri) {
        HttpEntity<Course> request = new HttpEntity<>(course);
        return restTemplateAuth().postForObject(uri, request, Course.class);
    }

    private Course putCourse(Course course, URI uri) {
        HttpEntity<Course> entity = new HttpEntity<>(course);
        return restTemplateAuth().exchange(uri, HttpMethod.PUT, entity, Course.class)
                                 .getBody();
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

    @Test
    void createCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");
        Course course = buildCourse("post course name", "post course description");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);

        assertThat(postedTeacherResponse).isNotNull();
        assertThat(postedTeacherResponse.getName()).isEqualTo(teacher.getName());
        assertThat(postedCourseResponse).isNotNull();
        assertThat(postedCourseResponse.getName()).isEqualTo(course.getName());
        assertThat(postedCourseResponse.getDescription()).isEqualTo(course.getDescription());
    }

    @Test
    void updateCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");
        Course course = buildCourse("post test case", "desc");
        System.out.println("Creatin");
        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);
        System.out.println("updating");
        postedCourseResponse.setName("update test case");
        uriCourse = buildCourseURI(postedTeacherResponse.getId(), postedCourseResponse.getId());
        Course updatedCourseResponse = putCourse(postedCourseResponse, uriCourse);

        assertThat(updatedCourseResponse).isNotNull();
        assertThat(updatedCourseResponse.getName()).isEqualTo(postedCourseResponse.getName());
    }

    @Test
    void getCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("get test case");
        Course course = buildCourse("get course name", "get course description");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);

        uriCourse = buildCourseURI(postedTeacherResponse.getId(), postedCourseResponse.getId());

        Course getCourseResponse = restTemplateAuth().getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNotNull();
        assertThat(getCourseResponse.getName()).isEqualTo(course.getName());
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

        URI uri = buildCourseURI(postedTeacherResponse.getId(), null);
        ResponseEntity<Course[]> courseResponse = restTemplateAuth().getForEntity(uri, Course[].class);
        List courseList = Arrays.asList(courseResponse.getBody());

        assertThat(courseResponse).isNotNull();
        assertThat(courseList.size()).isGreaterThanOrEqualTo(2);
        assertThat(courseList).extracting("name")
                              .contains(postedCourseResponse1.getName(), postedCourseResponse2.getName());
    }

    @Test
    void deleteTeacherSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("delete test case");
        Course course = buildCourse("delete course name", "delete course description");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);

        uriCourse = buildCourseURI(postedTeacherResponse.getId(), postedCourseResponse.getId());

        Course getCourseResponse = restTemplateAuth().getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNotNull();
        assertThat(getCourseResponse.getName()).isEqualTo(course.getName());

        restTemplateAuth().delete(uriCourse);
        getCourseResponse = restTemplateAuth().getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNull();
    }

}
