package com.example.todo_list.api;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/todos/index")
// 공통되는 부분들은 묶어버림
public class TodoApiController {

    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public List<Todo> index(){ return todoService.index(); }


    @PostMapping("/addTask")
    public ResponseEntity<Todo> addTask(@RequestBody TodoDto dto){
        Todo added = todoService.addTask(dto);
        return (added != null) ?
                ResponseEntity.status(HttpStatus.OK).body(added)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        String updatedStat = todoService.updateStatus(id, newStatus);
        return (updatedStat != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedStat)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/editTask/{id}")
    public ResponseEntity<TodoDto> editTask(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoDto editDto = todoService.editTask(id, dto);
        log.info(editDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(editDto);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<TodoDto> deleteTask(@PathVariable Long id){
        TodoDto deleteDto = todoService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

}
