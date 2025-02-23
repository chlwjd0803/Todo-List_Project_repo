package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @OneToMany(mappedBy = "user") // 유저에게 속한 투두들을 들고오기위함
    private List<Todo> todos;


    // 추후 개발 예정, 일단 모두 null값을 가질 것
//    @Column
//    private LocalDateTime createdAt;
//    @Column
//    private LocalDateTime updatedAt;
//    @Column
//    private LocalDateTime lastLoginAt;
//    @Column
//    private String account_status;
//    @Column
//    private String phone_number;

}
