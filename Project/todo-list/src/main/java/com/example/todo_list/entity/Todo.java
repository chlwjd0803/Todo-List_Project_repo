package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title; //작업 제목
    @Column
    private String status; //작업 상태(준비, 진행, 중단, 완료 등 4단계로 구성 예정)

}
