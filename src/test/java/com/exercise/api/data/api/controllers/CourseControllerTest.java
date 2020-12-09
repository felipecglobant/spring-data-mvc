package com.exercise.api.data.api.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.domain.Course;
import com.exercise.api.data.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private Teacher postTeacher(Teacher teacher, URI uri) {
        HttpEntity<Teacher> request = new HttpEntity<>(teacher);
        return restTemplate.postForObject(uri, request, Teacher.class);
    }

    private Course postCourse(Course course, URI uri) {
        HttpEntity<Course> request = new HttpEntity<>(course);
        return restTemplate.postForObject(uri, request, Course.class);
    }

    private URI buildCourseURI(Long teacherId, Long courseId) throws URISyntaxException {
        Map template = new HashMap<String, String>();
        template.put("teacher_id", teacherId.toString());
        if (Objects.isNull(courseId))
            return buildURI(template, ConstantURL.COURSES);
        else {
            return buildURI(template, ConstantURL.COURSES, courseId.toString());
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
    void getCourseSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("get test case");
        Course course = buildCourse("get course name", "get course description");

        URI uriTeacher = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uriTeacher);
        URI uriCourse = buildCourseURI(postedTeacherResponse.getId(), null);
        Course postedCourseResponse = postCourse(course, uriCourse);

        uriCourse = buildCourseURI(postedTeacherResponse.getId(), postedCourseResponse.getId());

        Course getCourseResponse = restTemplate.getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNotNull();
        assertThat(getCourseResponse.getName()).isEqualTo(course.getName());
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

        Course getCourseResponse = restTemplate.getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNotNull();
        assertThat(getCourseResponse.getName()).isEqualTo(course.getName());

        restTemplate.delete(uriCourse);
        getCourseResponse = restTemplate.getForObject(uriCourse, Course.class);

        assertThat(getCourseResponse).isNull();
    }

}
