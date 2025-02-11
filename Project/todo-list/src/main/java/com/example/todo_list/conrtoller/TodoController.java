package com.example.todo_list.conrtoller;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Category;
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

    @GetMapping("/todos/calendar")
    public String calendar(){
        return "/todos/calendar";
    }

//    @GetMapping("/todos/statistic")
//    public String statistic(){
//        return "/todos/statistic";
//    }

    // 전체 목록 보기, 서비스 사용
    @GetMapping("/todos/index")
    public String index(Model md){
        List<Category> categories = todoService.getCategories();
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
        Todo todo = dto.toEntity();
        Todo added = todoRepository.save(todo);
        return "redirect:/todos/index";
    }

}
