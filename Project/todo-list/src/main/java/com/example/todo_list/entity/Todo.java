package com.example.todo_list.entity;

import com.example.todo_list.dto.TodoDto;
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
    @Column
    private String category; // 작업의 카테고리

    public void patch(TodoDto dto) {
        if(this.id != dto.getId()) throw new IllegalArgumentException("잘못된 id가 입력되었습니다.");
        if(dto.getTitle() != null) this.title = dto.getTitle();
        //if(dto.getStatus() != null) this.status = dto.getStatus();
        if(dto.getCategory() != null) this.category = dto.getCategory();
    }
}
