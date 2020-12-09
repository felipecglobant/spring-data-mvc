package com.exercise.api.data.helpers;

public class ConstantURL{
    public static final String NAMESPACE = "/api";
    public static final String TEACHERS = NAMESPACE + "/teachers";
    public static final String STUDENTS = NAMESPACE + "/students";
    public static final String COURSES = TEACHERS + "/{teacher_id}/courses";
    public static final String COURSE = NAMESPACE + "/courses/{course_id}";
    public static final String COURSE_REGISTRATION =  "/students/{student_id}/register";
}