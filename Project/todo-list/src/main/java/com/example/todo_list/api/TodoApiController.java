package com.example.todo_list.api;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TodoApiController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/api/todos")
    public List<Todo> index(){ return todoService.index(); }

    @PostMapping("/api/todos")
    public ResponseEntity<Todo> addTask(@RequestBody TodoDto dto){
        Todo added = todoService.addTask(dto);
        return (added != null) ?
                ResponseEntity.status(HttpStatus.OK).body(added)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/todos/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        String updatedStat = todoService.updateStatus(id, newStatus);
        return (updatedStat != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedStat)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/todos/editTask/{id}")
    public ResponseEntity<TodoDto> taskEdit(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoDto editDto = todoService.taskEdit(id, dto);
        log.info(editDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(editDto);
    }

    @DeleteMapping("/api/todos/deleteTask/{id}")
    public ResponseEntity<TodoDto> taskDelete(@PathVariable Long id){
        TodoDto deleteDto = todoService.taskDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }



}
