package com.exercise.api.data.helpers;

public class ConstantURL{
    public static final String NAMESPACE = "/api";
    public static final String TEACHERS = NAMESPACE + "/teachers";
    public static final String STUDENTS = NAMESPACE + "/students";
    public static final String COURSES_BY_TEACHER = TEACHERS + "/{teacher_id}/courses";
    public static final String COURSE = NAMESPACE + "/courses";
    public static final String COURSE_REGISTRATION =  "/students/{student_id}/register";
}