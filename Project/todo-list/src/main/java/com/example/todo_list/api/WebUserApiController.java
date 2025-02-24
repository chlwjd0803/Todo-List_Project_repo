package com.example.todo_list.api;

import com.example.todo_list.dto.WebUserDto;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")

public class WebUserApiController {
    @Autowired
    private WebUserService webUserService;

    @PostMapping("/signup")
    public ResponseEntity<WebUser> signup(@RequestBody WebUserDto dto) {
        WebUser added = webUserService.signup(dto);
        return (added != null) ?
                ResponseEntity.status(HttpStatus.OK).body(added)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
