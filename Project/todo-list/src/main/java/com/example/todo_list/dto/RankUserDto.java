package com.example.todo_list.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankUserDto {
    private String username;
    private String progress;
    private int rank;
}
