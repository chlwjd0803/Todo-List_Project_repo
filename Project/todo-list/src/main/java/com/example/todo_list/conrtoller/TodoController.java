package com.example.todo_list.conrtoller;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.TodoRepository;
import com.example.todo_list.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    // 전체 목록 보기
    @GetMapping("/todos")
    public String index(Model md){
        List<Todo> todos = todoRepository.findAll();
        md.addAttribute("todoList", todos);
        return "todos/index";
    }

    @PostMapping("/todos/add")
    public String addTask(TodoDto dto){
        log.info(dto.toString());
        Todo todo = dto.toEntity();
        log.info(todo.toString());
        Todo added = todoRepository.save(todo);
        log.info(added.toString());
        return "redirect:/todos";
    }

}
