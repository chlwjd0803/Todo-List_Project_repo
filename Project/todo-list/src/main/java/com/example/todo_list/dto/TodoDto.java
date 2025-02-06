package com.example.todo_list.dto;

import com.example.todo_list.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class TodoDto {
    private Long id;
    private String title;
    private String status;
    private String category;

    public static TodoDto createTodoDto(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getStatus(),
                todo.getCategory()
        );
    }

    public Todo toEntity(){
        if(this.status == null) this.status = "준비";
        if(this.category == null || this.category.isEmpty()) this.category = "할일";
        return new Todo(id, title, status, category);
    }
}
