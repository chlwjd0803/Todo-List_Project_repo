package com.example.todo_list.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private List<TodoDto> todos;
}
