package com.example.todo_list.repository;

import com.example.todo_list.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    WebUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
