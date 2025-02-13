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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    // 카테고리 아이디가 넘어감, Dto는 넘어온 JSON
    @PatchMapping("/api/todos/categoryEdit/{id}")
    public ResponseEntity<CategoryDto> categoryEdit(@PathVariable Long id, @RequestBody Category dto){
        CategoryDto editDto = categoryService.categoryEdit(id, dto);
//        log.info(editDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(editDto);
    }

    @DeleteMapping("/api/todos/categoryDelete/{id}")
    public ResponseEntity<CategoryDto> categoryDelete(@PathVariable Long id){
        CategoryDto deleteDto = categoryService.categoryDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

    @DeleteMapping("/api/todos/categoryDelete/all")
    public ResponseEntity<List<CategoryDto>> categoryDeleteAll(){
        List<CategoryDto> deleteDtos = categoryService.categoryDeleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(deleteDtos);
    }

}
