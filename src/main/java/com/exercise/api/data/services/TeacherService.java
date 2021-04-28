package com.exercise.api.data.services;

import com.exercise.api.data.domain.Teacher;
import com.exercise.api.data.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.exercise.api.data.helpers.Utils.delay;
import static com.exercise.api.data.helpers.Utils.log;

@Service
public class TeacherService extends AbstractService<Teacher, TeacherRepository> {

    @Autowired
    public TeacherService(TeacherRepository repository){
        this.repository = repository;
    }

    // (1): operation_1 and operation_2 before operation_3
    // (2): operation_1 independent from operation_2
    public Optional<Teacher> create(Teacher teacher){
        CompletableFuture<Boolean> op1 = CompletableFuture.supplyAsync(() -> operation1(teacher));
        CompletableFuture<Boolean> op2 = CompletableFuture.supplyAsync(() -> operation2(teacher));

        Optional<Teacher> optionalEmpty = Optional.empty();

        return op1.thenCombine(op2, (_op1, _op2) -> _op1 && _op2)
                  .thenApply((op_results) -> {
                       if (op_results)
                           return operation3(teacher);
                       else
                           return optionalEmpty;
                   })
                  .join();
    }

    private boolean operation1(Teacher object){
        log("[op1] init saving teacher " + object.getName());
        delay(2000);
        log("[op1] finish saving teacher " + object.getName());

        return true;
    }

    private boolean operation2(Teacher object){
        log("[op2] init saving teacher " + object.getName());
        delay(1000);
        log("[op2] finish saving teacher " + object.getName());

        return true;
    }

    private Optional<Teacher> operation3(Teacher teacher){
        log("[op3] init saving teacher " + teacher.getName());
        delay(1000);
        Teacher savedTeacher = repository.save(teacher);
        log("[op3] finish saving teacher " + teacher.getName());

        return Optional.of(savedTeacher);
    }
}
