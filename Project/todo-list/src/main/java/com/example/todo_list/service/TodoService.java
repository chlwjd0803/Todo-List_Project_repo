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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    // 상태별로 나누어 정렬하기 위한 오버로딩 메소드, 데이터베이스 id순서 정렬
//    public List<Todo> index(String status) { return todoRepository.findByStatusOrderById(status); }

    // 상태별로 나누어 정렬하기 위한 오버로딩 메소드, 날짜 순서 정렬
    public List<Todo> index(String status) {
        return todoRepository.findByStatusAndWebUserIdOrderByDeadline(status, findCurUser().getId());
    }

    public List<Category> getCategories() {
        return categoryRepository.findByWebUserId(findCurUser().getId());
    }

    public List<Todo> today(String status) {
        return todoRepository.findByStatusAndWebUserIdAndDeadline(status, findCurUser().getId(), LocalDate.now());
    }


    public Todo addTasktoEntity(TodoDto dto){
        Todo todo = dto.toEntity();
        WebUser currentUser = findCurUser();

        // 단순히 이름 말고 유저의 것인지도 판단시켜야함
        Category category = categoryRepository.findByNameAndWebUserId(todo.getCategoryName(), currentUser.getId());
        if(category == null) {
            // 카테고리 새로 만들기
            category = new Category();
            category.setName(todo.getCategoryName());
            category.setWebUser(currentUser);
            categoryRepository.save(category);
        }
        todo.setCategory(category);
        todo.setWebUser(currentUser);

        return todo;
    }

    public Todo addTask(TodoDto dto) {
//        Todo todo = dto.toEntity();
//        WebUser currentUser = findCurUser();
//
//        // 단순히 이름 말고 유저의 것인지도 판단시켜야함
//        Category category = categoryRepository.findByNameAndWebUserId(todo.getCategoryName(), currentUser.getId());
//        if(category == null) {
//            // 카테고리 새로 만들기
//            category = new Category();
//            category.setName(todo.getCategoryName());
//            category.setWebUser(currentUser);
//            categoryRepository.save(category);
//        }
//        todo.setCategory(category);
//        todo.setWebUser(currentUser);
        Todo todo = addTasktoEntity(dto);
        return todoRepository.save(todo);
    }

    public Todo todayAddTask(TodoDto dto) {
//        Todo todo = dto.toEntity();
//        WebUser currentUser = findCurUser();
//
//        Category category = categoryRepository.findByNameAndWebUserId(todo.getCategoryName(), currentUser.getId());
//        if(category == null) {
//            // 카테고리 새로 만들기
//            category = new Category();
//            category.setName(todo.getCategoryName());
//            category.setWebUser(currentUser);
//            categoryRepository.save(category);
//        }
//        todo.setCategory(category);
//        todo.setWebUser(currentUser);
        Todo todo = addTasktoEntity(dto);
        todo.setDeadline(LocalDate.now());
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

        Category editCate = categoryRepository.findByNameAndWebUserId(dto.getCategory_name(), currentUser.getId());
        if(editCate == null) // 해당 이름으로 검색했는데 없다면
            throw new IllegalArgumentException("수정 실패, 대상 카테고리가 존재하지 않습니다.");

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
}
