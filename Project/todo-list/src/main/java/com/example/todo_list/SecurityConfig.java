package com.example.todo_list;

import com.example.todo_list.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserSecurityService userSecurityService;

    // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 기본적인 보안 정책 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 필요한 경우 CSRF 보호 설정 조정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/todos/signup").permitAll()  // 회원가입은 누구나 접근 가능
                        .requestMatchers("/api/todos/login").permitAll()
                        .requestMatchers("/todos/start").permitAll()
                        .requestMatchers("/todos/login").permitAll()
                        .requestMatchers("/todos/signup").permitAll()
                        .requestMatchers("/videos/**").permitAll()  // 정적 비디오 파일에 대한 접근 허용
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/css/**").permitAll()


//                        // 임시로 열어둠
//                        .requestMatchers("/todos/index/**").permitAll()
//                        .requestMatchers("/api/todos/index/**").permitAll()
//                        .requestMatchers("/js/**").permitAll()
                        .anyRequest().authenticated()  // 그 외는 인증 필요
                )
                .formLogin(form -> form
                .loginPage("/todos/login")  // 커스텀 로그인 페이지 경로
                .permitAll()
                );

        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userSecurityService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
