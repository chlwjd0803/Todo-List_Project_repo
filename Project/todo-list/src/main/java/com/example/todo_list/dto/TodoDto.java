package com.example.todo_list.dto;

import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@ToString
@Getter
public class TodoDto {
    private Long id;
    // 데이터 전송은 String으로 받되 엔티티와의 변환에서 String <-> Category 가 자유롭게 되어야함
    private String category_name;
    private String title;
    private String status;
    private String deadline_str;


    public static TodoDto createTodoDto(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getCategoryName(),
                todo.getTitle(),
                todo.getStatus(),
                todo.getDeadline()
        );
    }

    public Todo toEntity(){
        // this->DTO status는 문자열 그대로 쓰되 category는 문자열에서 객체로 변환하는 방식을 사용하여야함
        Category newCate = new Category(); // 미할당 상태로 선언

        // 카테고리 문자열이 null 이거나 빈 문자열일 경우
        // 카테고리가 미선택 되어있는 경우
        if(this.category_name == null || this.category_name.isEmpty()) return null;
        if(this.category_name.equals("전체")) {
            newCate.setName("할일"); // 기본 작업 이름으로 초기화
            this.category_name = newCate.getName(); // DTO에도 카테고리 할일로 지정했다고 저장
        }
        // 기존 카테고리에 맵핑하는 경우
        else {
            newCate.setName(this.category_name);
        }

        // String으로 받은 날짜를 Localdatetime 자료형에 맞게 변환하여야함
        LocalDate deadline;

        // 만약에 마감기한을 정해놓지 않는다면 null 값일수도 있음.
        // 또는 도달할 수 없는 연도인 9999년을 기본값으로 설정하게 만들 수도 있음.
        if(this.deadline_str != null && !this.deadline_str.isEmpty()){
            DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            deadline = LocalDate.parse(this.deadline_str, formatter);
        } else throw new IllegalArgumentException("날짜 입력이 잘못되었음.");

        return new Todo(id, newCate, this.title, this.status, deadline);
    }
}
