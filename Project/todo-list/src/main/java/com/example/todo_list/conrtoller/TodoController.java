package com.example.todo_list.conrtoller;

import com.example.todo_list.dto.StatusGroup;
import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.repository.TodoRepository;
import com.example.todo_list.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryRepository categoryRepository;

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

    // 전체 목록 보기
    @GetMapping("/todos/index")
    public String index(Model md){
        List<Category> categories = todoService.getCategories();
        List<Todo> readyTodos = todoService.index("준비");
        List<Todo> inProgressTodos = todoService.index("진행중");
        List<Todo> stoppedTodos = todoService.index("중단됨");
        List<Todo> completedTodos = todoService.index("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", readyTodos));
        statuses.add(new StatusGroup("진행중", inProgressTodos));
        statuses.add(new StatusGroup("중단됨", stoppedTodos));
        statuses.add(new StatusGroup("완료", completedTodos));

        md.addAttribute("categories", categories);
        md.addAttribute("statuses", statuses);
//        md.addAttribute("readyList", readyTodos);
//        md.addAttribute("inProgressList", inProgressTodos);
//        md.addAttribute("stoppedList", stoppedTodos);
//        md.addAttribute("completedList", completedTodos);
        return "todos/index";
    }
}
