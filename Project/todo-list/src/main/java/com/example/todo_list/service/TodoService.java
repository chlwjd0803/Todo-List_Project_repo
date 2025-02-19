package com.example.todo_list.service;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    // 기존 아이디 정렬
    public List<Todo> index() { return todoRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }
    // 상태별로 나누어 정렬하기 위한 오버로딩 메소드
    public List<Todo> index(String status) { return todoRepository.findByStatusOrderById(status); }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }


    public Todo addTask(TodoDto dto) {
        Todo todo = dto.toEntity();
        Category category = categoryRepository.findByName(todo.getCategoryName());
        if(category == null) {
            // 카테고리 새로 만들기
            category = new Category();
            category.setName(todo.getCategoryName());
            categoryRepository.save(category);
        }
        todo.setCategory(category);


        return todoRepository.save(todo);
    }

    @Transactional
    public String updateStatus(Long id, String newStatus) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("할 일을 찾을 수 없음"));
        //log.info(newStatus);
        // 아래를 단순 비교 연산자를 이용하면 오류가 발생하여 equals를 써야함
        if(Objects.equals(newStatus, "준비")) newStatus = "준비";
        else if(Objects.equals(newStatus, "진행중")) newStatus = "진행중";
        else if(Objects.equals(newStatus, "중단됨")) newStatus = "중단됨";
        else if(Objects.equals(newStatus, "완료")) newStatus = "완료";
        else throw new IllegalArgumentException("상태가 잘못됨");
        todo.setStatus(newStatus);
        todoRepository.save(todo);
        return newStatus;
    }

    @Transactional
    public TodoDto editTask(Long id, TodoDto dto) {
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 작업이 존재하지 않습니다."));
        log.info("데이터베이스에 있는 카테고리 이름 : " + target.getCategoryName());
        log.info("받아온 정보의 카테고리 이름 : " + dto.getCategory_name());

        Category editCate = categoryRepository.findByName(dto.getCategory_name());
        if(editCate == null)
            throw new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다.");

        target.patch(dto, editCate);
        Todo edited = todoRepository.save(target);
        return TodoDto.createTodoDto(edited);
    }

    @Transactional
    public TodoDto deleteTask(Long id) {
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제 대상 id가 없습니다."));
        todoRepository.delete(target);
        return TodoDto.createTodoDto(target);
    }
}
