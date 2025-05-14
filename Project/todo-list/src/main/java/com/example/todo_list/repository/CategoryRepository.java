package com.example.todo_list.repository;

import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    List<Category> findByWebUserId(Long Id);

    Optional<Category> findByNameAndWebUserId(String name, Long webUserId);

    @Override
    List<Category> findAll();
}
