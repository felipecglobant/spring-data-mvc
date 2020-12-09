package com.exercise.api.data.services;

import com.exercise.api.data.domain.Course;
import com.exercise.api.data.domain.Student;
import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.repositories.CourseRepository;
import com.exercise.api.data.repositories.StudentRepository;
import com.exercise.api.data.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService extends AbstractService<Course, CourseRepository> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public List<Course> getAllByTeacher(Long teacherId){
        return repository.findByTeacherId(teacherId);
    }

    public Optional<Course> persistByTeacher(Course course, Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isPresent()){
            Teacher teacher = teacherOptional.get();
            course.setTeacher(teacher);
            teacher.addCourse(course);
            repository.save(course);
            teacherRepository.save(teacher);

            return Optional.of(course);
        }
        return Optional.empty();
    }

    public Optional<Course> getByTeacher(Long id, Long teacherId) {
        return repository.findByIdAndTeacherId(id, teacherId);
    }

    public void removeByTeacher(Long id, Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacher.removeCourse(id);
            teacherRepository.save(teacher);
            repository.deleteById(id);
        }
    }

    public void registerStudent(Long courseId, Long studentId){
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = repository.findById(courseId);

        if (studentOptional.isPresent() && courseOptional.isPresent()){
            Student student = studentOptional.get();
            Course course = courseOptional.get();
            course.addStudent(student);
            repository.save(course);
        }
    }

    public void unregisterStudent(Long courseId, Long studentId){
        Optional<Course> courseOptional = repository.findById(courseId);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            course.removeStudent(studentId);
            repository.save(course);
        }
    }
}
