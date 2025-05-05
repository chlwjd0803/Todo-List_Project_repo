package com.example.todo_list.repository;

import com.example.todo_list.entity.Todo;
import com.example.todo_list.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Override
    List<Todo> findAll();

//    List<Todo> findByStatusOrderById(String status);

    List<Todo> findByWebUserId(Long webUserId);

    List<Todo> findByCategoryId(Long categoryId);

    List<Todo> findByStatusAndWebUserIdOrderByDeadline(String status, Long webUserId);

    List<Todo> findByStatusAndWebUserIdAndDeadline(String status, Long webUserId, LocalDate deadline);
}
