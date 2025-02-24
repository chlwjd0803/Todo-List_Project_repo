package com.example.todo_list.repository;

import com.example.todo_list.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    boolean findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
