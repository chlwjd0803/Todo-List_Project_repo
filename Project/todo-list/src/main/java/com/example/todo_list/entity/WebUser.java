package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique를 통해 유저이름이 중복되지 않도록 함
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public WebUser(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
