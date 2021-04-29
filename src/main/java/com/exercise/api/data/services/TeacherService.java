package com.exercise.api.data.services;

import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.domain.TeacherList;
import com.exercise.api.data.repositories.TeacherRepository;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.exercise.api.data.helpers.BatchStrategy.*;
import static com.exercise.api.data.helpers.Utils.delay;
import static com.exercise.api.data.helpers.Utils.log;

@Service
public class TeacherService extends AbstractService<Teacher, TeacherRepository> {

    @Value("${spring.datasource.maxActive}")
    private int databaseConnections;

    @Autowired
    public TeacherService(TeacherRepository repository){
        this.repository = repository;
    }

    public Optional<Teacher> create(Teacher teacher){
        return Optional.of(saveTeacher(teacher));
    }

    public Optional<TeacherList> createBatch(TeacherList teacherList, String strategy) {
        log("Strategy used: " + strategy);
        List<Teacher> teachers = teacherList.getTeachers();
        if (strategy.equals(SEQUENTIAL)) {
            teachers = teachers.stream()
                               .map((teacher) -> saveTeacher(teacher))
                               .collect(Collectors.toList());
        }
        else if (strategy.equals(EXECUTOR_SERVICE)) {
            ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber(teachers.size()));
            List<Future<Teacher>> futureTeachers = teachers.stream()
                                                           .map( (teacher) -> executorService.submit(() -> saveTeacher(teacher) ))
                                                           .collect(Collectors.toList());

            teachers = futureTeachers.stream()
                                     .map(teacherFuture -> {
                                        try {
                                            return teacherFuture.get();
                                        }
                                        catch (Exception e) {
                                            System.out.println("Exception: " + e.getMessage() );
                                            return null;
                                        }})
                                     .collect(Collectors.toList());

            executorService.shutdown();
        }
        else if (strategy.equals(COMPLETABLE_FUTURE)){
            List<CompletableFuture<Teacher>> compFutureTeachers = teachers.stream()
                .map( (teacher) -> CompletableFuture.supplyAsync(() -> saveTeacher(teacher) ))
                .collect(Collectors.toList());

            teachers = compFutureTeachers.stream()
                                         .map(CompletableFuture::join)
                                         .collect(Collectors.toList());
        }
        else if (strategy.equals(PARALLEL_STREAM)){
            teachers = teachers.parallelStream()
                               .map((teacher) -> saveTeacher(teacher))
                               .collect(Collectors.toList());
        }
        else {
            teachers = Collections.emptyList();
        }
        teacherList.setTeachers(teachers);

        return  Optional.of(teacherList);
    }

    private Teacher saveTeacher(Teacher teacher){
        log("init saving teacher " + teacher.getName());
        delay(2000);
        Teacher savedTeacher = repository.save(teacher);
        log("finish saving teacher " + teacher.getName());

        return savedTeacher;
    }

    private int threadsNumber(int collectionNumber) {
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        int threadsNumber = Math.min(collectionNumber, Math.min(processorsNumber, databaseConnections));

        log("Processors number: " + processorsNumber);
        log("Database connections: " + databaseConnections);
        log("Records number: " + collectionNumber);
        log("Threads number: " + threadsNumber);

        return threadsNumber;
    }
}
