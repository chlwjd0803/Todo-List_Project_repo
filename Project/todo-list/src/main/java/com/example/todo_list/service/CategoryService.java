package com.example.todo_list.service;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto categoryEdit(Long id, Category dto) {
        Category target = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다."));

        if(dto.getName() == null || dto.getName().isEmpty())
            throw new IllegalArgumentException("수정 실패, 카테고리 입력이 비었습니다.");

        target.setName(dto.getName());
        categoryRepository.save(target);

        return CategoryDto.createCategoryDto(target);
    }
}
