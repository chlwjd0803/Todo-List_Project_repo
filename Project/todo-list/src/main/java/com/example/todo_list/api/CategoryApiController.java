package com.example.todo_list.api;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CategoryApiController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;


    @PatchMapping("/api/todos/categoryEdit/{id}")
    public ResponseEntity<CategoryDto> categoryEdit(@PathVariable Long id, @RequestBody Category dto){
        CategoryDto editDto = categoryService.categoryEdit(id, dto);
        log.info(editDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(editDto);
    }

}
