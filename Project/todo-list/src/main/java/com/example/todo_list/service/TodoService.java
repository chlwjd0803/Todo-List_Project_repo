package com.example.todo_list.service;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Category;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.entity.WebUser;
import com.example.todo_list.repository.CategoryRepository;
import com.example.todo_list.repository.TodoRepository;
import com.example.todo_list.repository.WebUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WebUserRepository webUserRepository;

    private WebUser findCurUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 또는 ((UserDetails)authentication.getPrincipal()).getUsername();
        return webUserRepository.findByUsername(username);
        // 유저가 없을때 예외도 처리해줄것
    }

    // 기존 아이디 정렬, api 전송을 위해 남겨둠
    // 이 부분에서 유저의 것만 들고오게 변경
    public List<Todo> index() {
        WebUser currentUser = findCurUser();
        Long userId = currentUser.getId();
        return todoRepository.findByWebUserId(userId);
    }

    // 상태별로 나누어 정렬하기 위한 오버로딩 메소드, 날짜 순서 정렬
    public List<Todo> index(String status) {
        return todoRepository.findByStatusAndWebUserIdOrderByDeadline(status, findCurUser().getId());
    }

    public List<Todo> today(String status) {
        return todoRepository.findByStatusAndWebUserIdAndDeadline(status, findCurUser().getId(), LocalDate.now());
    }

    public List<Todo> favorite(String status) {
        return todoRepository.findByStatusAndWebUserIdAndFavorite(status, findCurUser().getId(), true);
    }

    private Todo addTasktoEntity(TodoDto dto){
        WebUser currentUser = findCurUser();
        Category selectCategory = categoryRepository.findByNameAndWebUserId(dto.getCategory_name(), currentUser.getId()).orElse(null);
        if(selectCategory == null){
            Category basicCategory = categoryRepository.findByNameAndWebUserId("작업", currentUser.getId()).orElse(null);
            if(basicCategory == null){
                selectCategory = new Category("작업", currentUser);
                categoryRepository.save(selectCategory);
            }
            else selectCategory = basicCategory;
        }

        return dto.toEntity(currentUser, selectCategory);
    }

    public Todo addTask(TodoDto dto) {
        Todo todo = addTasktoEntity(dto);
        return todoRepository.save(todo);
    }

    public Todo todayAddTask(TodoDto dto) {
        Todo todo = addTasktoEntity(dto);
        todo.setDeadline(LocalDate.now());
        return todoRepository.save(todo);
    }

    public Todo favoriteAddTask(TodoDto dto) {
        Todo todo = addTasktoEntity(dto);
        todo.setFavorite(true);
        return todoRepository.save(todo);
    }

    @Transactional
    public String updateStatus(Long id, String newStatus) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("할 일을 찾을 수 없음"));
        //log.info(newStatus);
        // 아래를 단순 비교 연산자를 이용하면 오류가 발생하여 equals를 써야함
        if(Objects.equals(newStatus, "준비")) newStatus = "준비";
        else if(Objects.equals(newStatus, "완료")) newStatus = "완료";
        else throw new IllegalArgumentException("상태가 잘못됨");
        todo.setStatus(newStatus);
        todoRepository.save(todo);
        return newStatus;
    }

    @Transactional
    public TodoDto editTask(Long id, TodoDto dto) {
        WebUser currentUser = findCurUser();
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 작업이 존재하지 않습니다."));
        log.info("데이터베이스에 있는 카테고리 이름 : " + target.getCategoryName());
        log.info("받아온 정보의 카테고리 이름 : " + dto.getCategory_name());

        Category editCate = categoryRepository.findByNameAndWebUserId(dto.getCategory_name(), currentUser.getId())
                .orElseThrow( () -> new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다."));

        target.patch(dto, editCate);
        Todo edited = todoRepository.save(target);
        return TodoDto.createTodoDto(edited);
    }

    @Transactional
    public TodoDto deleteTask(Long id) {
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제 대상 id가 없습니다."));
        todoRepository.delete(target);
        return TodoDto.createTodoDto(target);
    }


    public List<Todo> schedule() {
        return todoRepository.findByDeadlineIsNotNull();
    }

    public List<Todo> getTomorrowTodos() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return todoRepository.findByStatusAndWebUserIdAndDeadline("준비", findCurUser().getId(), tomorrow);
    }

    public List<Todo> getSevenDaysTodos(){
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(7);
        return todoRepository.findByStatusAndWebUserIdAndDeadlineBetween("준비", findCurUser().getId(),
                start, end);
    }

    public List<Todo> getFourteenDaysTodos(){
        LocalDate start = LocalDate.now().plusDays(8);
        LocalDate end = LocalDate.now().plusDays(14);
        return todoRepository.findByStatusAndWebUserIdAndDeadlineBetween("준비", findCurUser().getId(),
                start, end);
    }

    public Todo updateFavorite(Long id) {
        Todo target = todoRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("작업이 없습니다."));
        target.setFavorite(!target.getFavorite());
        return todoRepository.save(target);
    }



}
