package com.example.todo_list.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyPageDto {
    private String username;
    private double progress;
    private int rank;
    private List<RankUserDto> topUsers;
}
