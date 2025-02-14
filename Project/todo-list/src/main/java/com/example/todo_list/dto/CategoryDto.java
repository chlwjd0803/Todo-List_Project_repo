package com.example.todo_list.dto;

import com.example.todo_list.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
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

    public static List<CategoryDto> createCategoryDtoList(List<Category> categories){
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(createCategoryDto(category));
        }
        return dtos;
    }

    public Category toEntity() {
        return new Category(id, this.name, null);
    }
}
