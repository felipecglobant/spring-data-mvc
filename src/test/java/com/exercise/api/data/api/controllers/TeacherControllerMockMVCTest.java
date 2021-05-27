package com.exercise.api.data.api.controllers;

import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.domain.TeacherList;
import com.exercise.api.data.services.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.exercise.api.data.helpers.BatchStrategy.PARALLEL_STREAM;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "123", authorities = "API")
class TeacherControllerMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTeachersShouldReturnListOfTeachers() throws Exception {
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

        String expectedResponse = new JSONArray()
                .put(new JSONObject()
                                     .put("id", teacher1.getId())
                                     .put("name", teacher1.getName())
                                     .put("courses", new JSONArray(teacher1.getCourses())))
                .put(new JSONObject()
                                     .put("id", teacher2.getId())
                                     .put("name", teacher2.getName())
                                     .put("courses", new JSONArray(teacher2.getCourses())))
                .toString();

        // when
        this.mockMvc.perform(get("/api/teachers/"))
                .andDo(print())

        // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        verify(teacherService, times(1)).getAll();
    }

    @Test
    void createTeacherShouldReturnNewTeacher() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name updated")
                .courses(Collections.emptyList())
                .build();
        when(teacherService.create(any(Teacher.class))).thenReturn(Optional.of(teacher));
        String expectedResponse = new JSONObject()
                .put("id", teacher.getId())
                .put("name", teacher.getName())
                .put("courses", new JSONArray(teacher.getCourses()))
                .toString();

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/teachers")
                .content(asJsonString(teacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        verify(teacherService, times(1)).create(any(Teacher.class));
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
        String expectedResponse = new JSONObject()
                .put("id", teacher.getId())
                .put("name", teacher.getName())
                .put("courses", new JSONArray(teacher.getCourses()))
                .toString();

        // when
        this.mockMvc.perform(get("/api/teachers/{id}", teacher.getId()))
                    .andDo(print())

        // then
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse));
        verify(teacherService, times(1)).get(anyLong());
    }

    @Test
    void updateTeacherShouldReturnUpdatedTeacher() throws Exception {
        // given
        Teacher teacher = Teacher.builder()
                .id(Long.valueOf(1))
                .name("Teacher test name updated")
                .courses(Collections.emptyList())
                .build();
        when(teacherService.update(any(Teacher.class))).thenReturn(Optional.of(teacher));
        String expectedResponse = new JSONObject()
                .put("id", teacher.getId())
                .put("name", teacher.getName())
                .put("courses", new JSONArray(teacher.getCourses()))
                .toString();

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/api/teachers/{id}", teacher.getId())
                .content(asJsonString(teacher))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        verify(teacherService, times(1)).update(any(Teacher.class));
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
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/teachers/{id}", teacher.getId()))

        // then
                .andExpect(status().isOk());
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

        when(teacherService.createBatch(any(TeacherList.class), anyString())).thenReturn(Optional.of(teacherList));

        String expectedResponse = new JSONObject().put("teachers",
                                                        new JSONArray()
                                                            .put(new JSONObject()
                                                                .put("id", teacher1.getId())
                                                                .put("name", teacher1.getName())
                                                                .put("courses", new JSONArray(teacher1.getCourses())))
                                                            .put(new JSONObject()
                                                                .put("id", teacher2.getId())
                                                                .put("name", teacher2.getName())
                                                                .put("courses", new JSONArray(teacher2.getCourses())))
                                                        ).toString();

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/teachers/batch/")
                .content(asJsonString(teacherList))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        verify(teacherService, times(1)).createBatch(any(TeacherList.class), anyString());

        // when
        this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/teachers/batch/?strategy={strategy}", PARALLEL_STREAM)
            .content(asJsonString(teacherList))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

            // then
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
        verify(teacherService, times(2)).createBatch(any(TeacherList.class), anyString());
    }
}