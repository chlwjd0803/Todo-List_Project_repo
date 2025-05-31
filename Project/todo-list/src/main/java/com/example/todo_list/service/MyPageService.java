package com.example.todo_list.service;

import com.example.todo_list.dto.MyPageDto;
import com.example.todo_list.dto.RankUserDto;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.TodoRepository;
import com.example.todo_list.repository.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MyPageService {

    private final TodoRepository todoRepository;
    private final WebUserRepository webUserRepository;

    @Autowired
    public MyPageService(TodoRepository todoRepository,
                         WebUserRepository webUserRepository) {
        this.todoRepository = todoRepository;
        this.webUserRepository = webUserRepository;
    }

    public MyPageDto getMyPageInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsername = authentication.getName();
        WebUser currentUser = webUserRepository.findByUsername(loginUsername);
        if (currentUser == null) {
            throw new IllegalStateException("로그인된 사용자 정보를 찾을 수 없습니다.");
        }
        Long myUserId = currentUser.getId();

        long myTotal = todoRepository.countByWebUserId(myUserId);
        long myCompleted = todoRepository.countByWebUserIdAndStatus(myUserId, "완료");

        double myProgress = 0.0;
        if (myTotal > 0) {
            myProgress = (myCompleted * 100.0) / (double) myTotal;
        }

        List<UserProgressTmp> tmpList = new ArrayList<>();

        for (WebUser user : webUserRepository.findAll()) {
            Long otherId = user.getId();
            long otherTotal = todoRepository.countByWebUserId(otherId);
            long otherCompleted = todoRepository.countByWebUserIdAndStatus(otherId, "완료");

            double otherProgressDouble = 0.0;
            if (otherTotal > 0) {
                otherProgressDouble = (otherCompleted * 100.0) / (double) otherTotal;
            }
            tmpList.add(new UserProgressTmp(user.getUsername(), otherProgressDouble, otherId));
        }

        tmpList.sort(Comparator.comparing(UserProgressTmp::getProgressDouble).reversed());

        List<RankUserDto> topUsers = new ArrayList<>();
        int myRank = 0;

        for (int i = 0; i < tmpList.size(); i++) {
            UserProgressTmp up = tmpList.get(i);
            int rank = i + 1; // 순위: 1부터 시작

            if (i < 10) {
                String formattedProgress = String.format("%.1f", up.getProgressDouble());
                topUsers.add(new RankUserDto(up.getUsername(), formattedProgress, rank));
            }

            if (up.getUserId().equals(myUserId)) {
                myRank = rank;
            }
        }

        // 6) MyPageDto에 담아 반환
        return new MyPageDto(currentUser.getUsername(), myProgress, myRank, topUsers);
    }

    private static class UserProgressTmp {
        private final String username;
        private final double progressDouble;
        private final Long userId;

        public UserProgressTmp(String username, double progressDouble, Long userId) {
            this.username = username;
            this.progressDouble = progressDouble;
            this.userId = userId;
        }
        public String getUsername() {
            return username;
        }
        public double getProgressDouble() {
            return progressDouble;
        }
        public Long getUserId() {
            return userId;
        }
    }
}
