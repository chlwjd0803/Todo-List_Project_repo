package com.example.todo_list.service;

import com.example.todo_list.dto.WebUserDto;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.WebUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebUserService {
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public WebUser signup(WebUserDto dto) {
        if(webUserRepository.existsByUsername(dto.getUsername())){
            log.info("이미 사용중인 사용자 이름입니다.");
            return null;
        }

        if(webUserRepository.existsByEmail(dto.getEmail())){
            log.info("이미 사용중인 이메일 입니다.");
            return null;
        }


        WebUser webUser = new WebUser();
        webUser.setUsername(dto.getUsername());
        webUser.setEmail(dto.getEmail());
        webUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        return webUserRepository.save(webUser);
    }
}
