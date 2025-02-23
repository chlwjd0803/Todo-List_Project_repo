package com.example.todo_list.dto;

import com.example.todo_list.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;

    public User toEntity() {
        return null;
    }


    // 보안과 관련된 고려사항
    // 1. password 필드에 대한 노출 조심
    // 2. setter를 없애고 값을 변경해야할때는 새로운 객체를 생성하는 방법을 고려
}
