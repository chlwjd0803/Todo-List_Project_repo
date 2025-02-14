package com.example.todo_list.dto;

import com.example.todo_list.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// 뷰 페이지 표시 코드를 단축하기 위한 클래스

@AllArgsConstructor
@Getter
public class StatusGroup {
    private String status_name;
    private List<Todo> list;
}
