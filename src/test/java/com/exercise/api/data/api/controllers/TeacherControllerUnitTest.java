package com.exercise.api.data.api.controllers;

import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.domain.TeacherList;
import com.exercise.api.data.services.TeacherService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerUnitTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController controller;

    @Test
    void teachersShouldReturnListOfTeachers() {
        // given
        Teacher teacher1 = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name")
                .courses(Collections.emptyList())
                .build();
        Teacher teacher2 = Teacher.builder()
                .id(Long.valueOf(2))
                .name("Teacher test name 2")
                .courses(Collections.emptyList())
                .build();
        List<Teacher> teacherList = Arrays.asList(teacher1, teacher2);

        when(teacherService.getAll()).thenReturn(teacherList);

        // when
        List<Teacher> returnedTeacherList = controller.teachers();

        // then
        verify(teacherService, times(1)).getAll();
        assertThat(returnedTeacherList, is(not(empty())));
        assertThat(returnedTeacherList, contains(samePropertyValuesAs(teacher1), samePropertyValuesAs(teacher2)));
    }

    @Test
    void createTeacherShouldReturnNewTeacher() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name updated")
                .courses(Collections.emptyList())
                .build();
        when(teacherService.create(teacher)).thenReturn(Optional.of(teacher));

        // when
        Optional<Teacher> optionalCreatedTeacher = controller.createTeachers(teacher);
        Teacher createdTeacher = optionalCreatedTeacher.get();

        // then
        verify(teacherService, times(1)).create(teacher);
        assertThat(optionalCreatedTeacher.isPresent(), is(true));
        assertThat(createdTeacher, samePropertyValuesAs(teacher));
    }

    @Test
    void getTeacherShouldReturnExpectedRecord() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                                 .id(Long.valueOf(1))
                                 .name("Teacher test name")
                                 .courses(Collections.emptyList())
                                 .build();
        when(teacherService.get(teacher.getId())).thenReturn(Optional.of(teacher));

        // when
        Optional<Teacher> optionalReturnedTeacher = controller.getTeacher(teacher.getId());
        Teacher returnedTeacher = optionalReturnedTeacher.get();

        // then
        verify(teacherService, times(1)).get(anyLong());
        assertThat(optionalReturnedTeacher.isPresent(), is(true));
        assertThat(returnedTeacher, samePropertyValuesAs(teacher));
    }

    @Test
    void updateTeacherShouldReturnUpdatedTeacher() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name updated")
                .courses(Collections.emptyList())
                .build();
        when(teacherService.update(teacher)).thenReturn(Optional.of(teacher));

        // when
        Optional<Teacher> optionalUpdatedTeacher = controller.updateTeacher(teacher, teacher.getId());
        Teacher updatedTeacher = optionalUpdatedTeacher.get();

        // then
        verify(teacherService, times(1)).update(teacher);
        assertThat(optionalUpdatedTeacher.isPresent(), is(true));
        assertThat(updatedTeacher, samePropertyValuesAs(teacher));
    }

    @Test
    void deleteTeacher() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name updated")
                .courses(Collections.emptyList())
                .build();

        // when
        controller.deleteTeacher(teacher.getId());

        // then
        verify(teacherService, times(1)).delete(teacher.getId());
    }

    @Test
    void createTeachersInBatchReturnsCreatedList() throws Exception {
        // given
        Teacher teacher1 = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name")
                .courses(Collections.emptyList())
                .build();
        Teacher teacher2 = Teacher.builder()
                .id(Long.valueOf(2))
                .name("Teacher test name 2")
                .courses(Collections.emptyList())
                .build();
        TeacherList teacherList = TeacherList.builder()
                                             .teachers(Arrays.asList(teacher1, teacher2))
                                             .build();

        when(teacherService.createBatch(eq(teacherList), anyString())).thenReturn(Optional.of(teacherList));

        // when
        Optional<TeacherList> optionalCreatedTeachersList = controller.createTeachersList(teacherList, null);
        TeacherList createdTeacherList = optionalCreatedTeachersList.get();

        // then
        verify(teacherService, times(1)).createBatch(eq(teacherList), anyString());
        assertThat(optionalCreatedTeachersList.isPresent(), is(true));
        assertThat(createdTeacherList.getTeachers(), contains(samePropertyValuesAs(teacher1), samePropertyValuesAs(teacher2)));
    }
}