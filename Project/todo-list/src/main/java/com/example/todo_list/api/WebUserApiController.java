package com.example.todo_list.api;

import com.example.todo_list.dto.WebUserDto;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.service.WebUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@Slf4j

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody WebUserDto dto) {
        String token = webUserService.login(dto);
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // 쿠키 생성 (24시간 유효)
        ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)
                .path("/")
                .maxAge(86400) // 24시간
                .build();

        // 쿠키 저장을 위하여 헤더부분 추가
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        ResponseCookie cookie = webUserService.logout();
        log.info("로그아웃 백엔드 컨트롤러");

        return (cookie != null) ? ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body("로그아웃 성공")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 오류");
    }
}
