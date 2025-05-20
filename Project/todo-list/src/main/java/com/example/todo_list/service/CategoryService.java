package com.example.todo_list.service;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.repository.TodoRepository;
import com.example.todo_list.repository.WebUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private WebUserRepository webUserRepository;

    private WebUser findCurUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 또는 ((UserDetails)authentication.getPrincipal()).getUsername();
        return webUserRepository.findByUsername(username);
    }

    public Category categoryAdd(CategoryDto dto) {
        WebUser currentUser = findCurUser();

        if(dto.getName() == null || dto.getName().equals("")){
            log.info("카테고리 이름이 입력되지 않았거나 공백입니다.");
            return null;
        }
        if(categoryRepository.findByNameAndWebUserId(dto.getName(), currentUser.getId()).orElse(null) != null){
            log.info("카테고리 이름이 이미 존재합니다.");
            return null;
        }
        Category category = new Category(dto.getName(), currentUser);
        category.setWebUser(currentUser); // 현재 로그인한 사용자의 것으로 만듦

        return categoryRepository.save(category);
    }

    @Transactional
    public CategoryDto categoryEdit(Long id, CategoryDto dto) {
        Category target = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다."));

        if(dto.getName() == null || dto.getName().isEmpty())
            throw new IllegalArgumentException("수정 실패, 카테고리 입력이 비었습니다.");

        target.setName(dto.getName());
        categoryRepository.save(target);

        return CategoryDto.createCategoryDto(target);
    }

    @Transactional
    public CategoryDto categoryDelete(Long id) {
        Category target = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제 실패, 삭제할 카테고리가 존재하지 않습니다."));
        List<Todo> todos = todoRepository.findByCategoryId(id);
        // 카테고리 안에 위치한 작업들을 모두 삭제함
        todoRepository.deleteAll(todos);
        categoryRepository.delete(target);
        return CategoryDto.createCategoryDto(target);
    }

    @Transactional
    public List<CategoryDto> categoryDeleteAll() {
        WebUser currentUser = findCurUser();
        // 해당하는 유저의 것만 일괄 삭제하므로 해당 유저의 정보만 얻어옴
        List<Category> cateAll = categoryRepository.findByWebUserId(currentUser.getId());
        List<Todo> todoAll = todoRepository.findByWebUserId(currentUser.getId());
        todoRepository.deleteAll(todoAll);
        categoryRepository.deleteAll(cateAll);
        return CategoryDto.createCategoryDtoList(cateAll);
    }


}
