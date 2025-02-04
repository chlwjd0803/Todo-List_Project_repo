package com.example.todo_list.dto;

import com.example.todo_list.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class TodoDto {
    private Long id;
    private String title;
    private String status;

    public Todo toEntity(){
        if(this.status == null) this.status = "준비";
        return new Todo(id, title, status);
    }
}
