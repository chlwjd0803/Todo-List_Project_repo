package com.example.todo_list.service;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TodoRepository todoRepository;

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

    @Transactional
    public CategoryDto categoryDelete(Long id) {
        Category target = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제 실패, 삭제할 카테고리가 존재하지 않습니다."));
        List<Todo> todos = todoRepository.findByCategoryId(id);
        // 카테고리 안에 위치한 작업들을 모두 삭제함
        todoRepository.deleteAll(todos);
        categoryRepository.delete(target);
        return CategoryDto.createCategoryDto(target);
    }
}
