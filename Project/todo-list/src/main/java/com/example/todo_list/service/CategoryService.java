package com.example.todo_list.service;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDto categoryEdit(Long id, Category dto) {
        Category target = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다."));

        target.setName(dto.getName());
        categoryRepository.save(target);

        return CategoryDto.createCategoryDto(target);

    }
}
