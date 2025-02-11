package com.example.todo_list.dto;

import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class TodoDto {
    private Long id;
    private Category category;
    private String title;
    private String status;


    public static TodoDto createTodoDto(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getCategory(),
                todo.getTitle(),
                todo.getStatus()
        );
    }

    public Todo toEntity(){
        // 사실 상태는 무조건 준비로 할 것이기 때문에 null 일 것임
        if(this.status == null) this.status = "준비";
        if(this.category.getName() == null || this.category.isEmpty()) this.category.setName("할일");
        return new Todo(id, category, title, status);
    }
}
