package com.example.todo_list.service;

import com.example.todo_list.JwtUtil;
import com.example.todo_list.dto.WebUserDto;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.WebUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class WebUserService {
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

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

    public String login(WebUserDto dto) {
        WebUser webUser = webUserRepository.findByUsername(dto.getUsername());
        if(webUser == null){
            log.info("사용자정보가 없습니다.");
            return null;
        }
        if(!passwordEncoder.matches(dto.getPassword(), webUser.getPassword())){
            log.info("비밀번호가 틀렸습니다.");
            return null;
        }
        return jwtUtil.generateToken(webUser);
    }

    public ResponseCookie logout() {
        return ResponseCookie.from("jwtToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
    }
}
