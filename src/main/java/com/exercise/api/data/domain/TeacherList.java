package com.exercise.api.data.domain;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class TeacherList {

    private List<Teacher> teachers;

    public TeacherList() {
        teachers = Collections.emptyList();
    }
}
