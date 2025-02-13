package com.example.todo_list.dto;

import com.example.todo_list.entity.Category;
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
//    private List<TodoDto> todos;

    public static CategoryDto createCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
