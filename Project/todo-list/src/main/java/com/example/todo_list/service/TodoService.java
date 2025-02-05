package com.example.todo_list.service;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> index() { return todoRepository.findAll(); }


    public Todo addTask(TodoDto dto) {
        Todo todoEntity = dto.toEntity();
        if(todoEntity.getId() != null) return null;
        return todoRepository.save(todoEntity);
    }

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
}
