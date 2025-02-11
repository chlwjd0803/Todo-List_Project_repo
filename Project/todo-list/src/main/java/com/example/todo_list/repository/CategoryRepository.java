package com.example.todo_list.repository;

import com.example.todo_list.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);

    @Override
    List<Category> findAll();
}
