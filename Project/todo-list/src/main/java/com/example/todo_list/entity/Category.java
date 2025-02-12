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

    // 카테고리 이름을 저장하는 필드. 기존의 category 필드와 같은 역할.
    @Column(nullable = false)
    private String name;

    // 양방향 관계 설정: 해당 카테고리에 속한 Todo 리스트.
    // 카테고리 하나가 여러개의 Todo 작업의 값에 대응되므로
    @OneToMany(mappedBy = "category")
    private List<Todo> todos;

    public boolean isEmpty() {
        return name.isEmpty();
    }
}

