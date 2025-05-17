package com.example.todo_list.dto;

import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.entity.WebUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@ToString
@Getter
public class TodoDto {
    private Long id;
    private Long webUserId;
    // 데이터 전송은 String으로 받되 엔티티와의 변환에서 String <-> Category 가 자유롭게 되어야함
    private String category_name;
    private String title;
    private String status;
    private String deadline_str;


    public static TodoDto createTodoDto(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getWebUser().getId(),
                todo.getCategoryName(),
                todo.getTitle(),
                todo.getStatus(),
                todo.getDeadline()
        );
    }

    public Todo toEntity(WebUser curUser, Category selectCategory){
        // this->DTO status는 문자열 그대로 쓰되 category는 문자열에서 객체로 변환하는 방식을 사용하여야함
        // String으로 받은 날짜를 Localdatetime 자료형에 맞게 변환하여야함
        LocalDate deadline;

        // 만약에 마감기한을 정해놓지 않는다면 null 값일수도 있음.
        if(this.deadline_str != null && !this.deadline_str.isEmpty()){
            DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            deadline = LocalDate.parse(this.deadline_str, formatter);
        } else deadline = null;


        // 일단 유저는 미지정 상태로 넘기기
        // 이때 넘기는 newCate도 가상이므로 크게 신경쓰지 않아도 됨
        return new Todo(curUser, selectCategory, this.title, this.status, deadline, false);
    }
}
