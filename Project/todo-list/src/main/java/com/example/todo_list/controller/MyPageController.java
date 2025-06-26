package com.example.todo_list.controller;

import com.example.todo_list.dto.MyPageDto;
import com.example.todo_list.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class MyPageController {

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/mypage")
    public String showMyPage(Model model) {
        MyPageDto dto = myPageService.getMyPageInfo();

        model.addAttribute("username", dto.getUsername());
        model.addAttribute("progress", dto.getProgress());
        model.addAttribute("rank", dto.getRank());
        model.addAttribute("topUsers", dto.getTopUsers());

        return "todos/mypage";
    }
}
