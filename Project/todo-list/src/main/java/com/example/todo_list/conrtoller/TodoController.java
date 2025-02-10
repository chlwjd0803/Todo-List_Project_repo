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

    // 시작화면 테스트중
    @GetMapping("/todos/start")
    public String start(){
        return "/todos/start";
    }



    // 전체 목록 보기, 서비스 사용
    @GetMapping("/todos/index")
    public String index(Model md){
        // List<Todo> todos = todoService.index();
        // md.addAttribute("todoList", todos);

        List<String> categories = todoService.getCategories(); // 중복되지 않는 카테고리 들고오기
        List<Todo> readyTodos = todoService.index("준비");
        List<Todo> inProgressTodos = todoService.index("진행중");
        List<Todo> stoppedTodos = todoService.index("중단됨");
        List<Todo> completedTodos = todoService.index("완료");

        md.addAttribute("categories", categories);
        md.addAttribute("readyList", readyTodos);
        md.addAttribute("inProgressList", inProgressTodos);
        md.addAttribute("stoppedList", stoppedTodos);
        md.addAttribute("completedList", completedTodos);
        return "todos/index";
    }

    // 서비스 미사용(컨트롤러에서 모두 처리)
    @PostMapping("/todos/index/add")
    public String addTask(TodoDto dto){
        log.info(dto.toString());
        Todo todo = dto.toEntity();
        log.info(todo.toString());
        Todo added = todoRepository.save(todo);
        log.info(added.toString());
        return "redirect:/todos/index";
    }

}
