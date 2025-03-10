package com.example.todo_list.entity;

import com.example.todo_list.dto.TodoDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    // 기존의 문자열 형태의 카테고리 대신, Category 엔티티와 연관관계 설정.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
//    @ManyToOne
//    @JoinColumn(name = "user_id") // 해당 작업은 유저 하나에 속하기 때문
//    private User user;
    @Column
    private String title; //작업 제목
    @Column
    private String status; //작업 상태(준비, 완료 두 가지 구성만 사용할것)
    @Column
    private LocalDate deadline;

    // DTO에서 카테고리 이름을 문자열로 반환받기 위함
    public String getCategoryName() {
        return category.getName();
    }

    public String getDeadline(){
        if(this.deadline == null){
            return "기한 없음";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.deadline.format(formatter);
    }


    public void patch(TodoDto dto, Category editCate) {
        if(this.id != dto.getId()) throw new IllegalArgumentException("잘못된 id가 입력되었습니다.");
        if(dto.getTitle() != null) this.title = dto.getTitle();

//        if(dto.getCategory_name() != null) this.category.setName(dto.getCategory_name());
        if(dto.getCategory_name() != null){
            this.category = editCate;
        }
        if(dto.getDeadline_str() != null){
            DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.deadline = LocalDate.parse(dto.getDeadline_str(), formatter);
        }
    }

}
