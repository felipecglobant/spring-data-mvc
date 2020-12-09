package com.exercise.api.data.repositories;

import com.exercise.api.data.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);

    Optional<Course> findByIdAndTeacherId(Long id, Long teacherId);
}
