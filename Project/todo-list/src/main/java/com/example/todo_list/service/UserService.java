package com.example.todo_list.service;

import com.example.todo_list.dto.UserDto;
import com.example.todo_list.entity.User;
import com.example.todo_list.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public User signup(UserDto dto) {
        // 엔티티로 변환한후
        User user = dto.toEntity();

        // 회원 무결성 검사? 후, 미완성

        // 저장
        return userRepository.save(user);
    }
}
