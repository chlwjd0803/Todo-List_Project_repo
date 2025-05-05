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
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    // 시작화면 테스트중
    @GetMapping("/start")
    public String start(){
        return "/todos/start";
    }

    @GetMapping("/calendar")
    public String calendar(){
        return "/todos/calendar";
    }

    @GetMapping("/login")
    public String login(){
        return "/todos/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "/todos/signup";
    }

//    @GetMapping("/todos/statistic")
//    public String statistic(){
//        return "/todos/statistic";
//    }

    // 전체 목록 보기
    @GetMapping("/index")
    public String index(Model md){
        List<Category> categories = todoService.getCategories();
        List<Todo> readyTodos = todoService.index("준비");
        List<Todo> completedTodos = todoService.index("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", readyTodos));
        statuses.add(new StatusGroup("완료", completedTodos));

        md.addAttribute("categories", categories);
        md.addAttribute("statuses", statuses);
        return "todos/index";
    }

    @GetMapping("/today")
    public String today(Model md){
        List<Todo> todayReadyTodos = todoService.today("준비");
        List<Todo> todayCompletedTodos = todoService.today("완료");

        List<StatusGroup> statuses = new ArrayList<>();
        statuses.add(new StatusGroup("준비", todayReadyTodos));
        statuses.add(new StatusGroup("완료", todayCompletedTodos));

        md.addAttribute("statuses", statuses);
        return "todos/today";
    }
}
