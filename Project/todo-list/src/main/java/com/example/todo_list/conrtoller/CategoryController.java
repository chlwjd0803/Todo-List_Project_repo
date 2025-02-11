package com.example.todo_list.conrtoller;

import com.example.todo_list.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

}
