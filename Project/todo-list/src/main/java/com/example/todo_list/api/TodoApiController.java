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
@RequestMapping("/api/todos")
// 공통되는 부분들은 묶어버림
public class TodoApiController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/index")
    public List<Todo> index(){ return todoService.index(); }


    @PostMapping("/index/addTask")
    public ResponseEntity<Todo> addTask(@RequestBody TodoDto dto){
        Todo added = todoService.addTask(dto);
        return (added != null) ?
                ResponseEntity.status(HttpStatus.OK).body(added)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/today/addTask")
    public ResponseEntity<Todo> todayAddTask(@RequestBody TodoDto dto){
        Todo todayAdded = todoService.todayAddTask(dto);
        return (todayAdded != null) ?
                ResponseEntity.status(HttpStatus.OK).body(todayAdded)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/index/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        String updatedStat = todoService.updateStatus(id, newStatus);
        return (updatedStat != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedStat)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/updateFavorite/{id}")
    public ResponseEntity<?> updateFavorite(@PathVariable Long id){
        Todo check = todoService.updateFavorite(id);
        return (check!=null) ? ResponseEntity.ok(check) :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping("/index/editTask/{id}")
    public ResponseEntity<TodoDto> editTask(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoDto editDto = todoService.editTask(id, dto);
        log.info(editDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(editDto);
    }

    @DeleteMapping("/index/deleteTask/{id}")
    public ResponseEntity<TodoDto> deleteTask(@PathVariable Long id){
        TodoDto deleteDto = todoService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

}
