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
//카테고리가 더 큰 범위의 데이터가 됨을 인지하여야함
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "webuser_id")
    private WebUser webUser;

    // 카테고리 이름을 저장하는 필드. 기존의 category 필드와 같은 역할.
    @Column(nullable = false)
    private String name;
}

