package com.example.todo_list.service;

import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.WebUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private WebUserRepository webUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<WebUser> _webUser = this.webUserRepository.findByUsername(username);
        if(_webUser.isEmpty()) throw new UsernameNotFoundException("사용자를 찾을 수 없음");
        WebUser webUser = _webUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(webUser.getUsername(), webUser.getPassword(), authorities);
    }
}
