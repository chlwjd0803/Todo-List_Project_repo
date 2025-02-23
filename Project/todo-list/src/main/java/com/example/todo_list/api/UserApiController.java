package com.example.todo_list.api;

import com.example.todo_list.dto.UserDto;
import com.example.todo_list.entity.User;
import com.example.todo_list.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")

public class UserApiController {
    @Autowired
    private UserService userService;

//    @PostMapping("/signup")
//    public void signup(@RequestBody UserDto dto) {
//        User added = userService.signup(dto);
//    }
}
