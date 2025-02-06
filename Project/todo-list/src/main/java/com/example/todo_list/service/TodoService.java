package com.example.todo_list.service;

import com.example.todo_list.dto.TodoDto;
import com.example.todo_list.entity.Todo;
import com.example.todo_list.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    // 기존 아이디 정렬
    public List<Todo> index() { return todoRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }
    // 상태별로 나누어 정렬하기 위한 오버로딩 메소드
    public List<Todo> index(String status) { return todoRepository.findByStatusOrderById(status); }

    public List<String> getCategories() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(Todo::getCategory).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        // 1. stream : Stream API를 사용하여 함수형 프로그래밍 스타일로 처리함
        // 2. map(Todo:getCategory) : 각 객체를 해당 카테고리 문자열로 변환함, 메소드 참조 문법임, Stream<String>
        // 3. filter(Object::nonNull : null이 아닌 문자만 확인
        // (중요)4. distinct() : 중복값 제거
        // 5. collect(Collectors.toList()) : 처리한 카테고리 문자열들을 List형으로 반환하는 것
    }


    public Todo addTask(TodoDto dto) {
        Todo todoEntity = dto.toEntity();
        if(todoEntity.getId() != null) return null;
        return todoRepository.save(todoEntity);
    }

    @Transactional
    public String updateStatus(Long id, String newStatus) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("할 일을 찾을 수 없음"));
        //log.info(newStatus);
        // 아래를 단순 비교 연산자를 이용하면 오류가 발생하여 equals를 써야함
        if(Objects.equals(newStatus, "준비")) newStatus = "준비";
        else if(Objects.equals(newStatus, "진행중")) newStatus = "진행중";
        else if(Objects.equals(newStatus, "중단됨")) newStatus = "중단됨";
        else if(Objects.equals(newStatus, "완료")) newStatus = "완료";
        else throw new IllegalArgumentException("상태가 잘못됨");
        todo.setStatus(newStatus);
        todoRepository.save(todo);
        return newStatus;
    }

    @Transactional
    public TodoDto taskEdit(Long id, TodoDto dto) {
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정 실패, 대상 작업이 존재하지 않습니다."));
        target.patch(dto);
        Todo edited = todoRepository.save(target);
        return TodoDto.createTodoDto(edited);
    }

    @Transactional
    public TodoDto taskDelete(Long id) {
        Todo target = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제 대상 id가 없습니다."));
        todoRepository.delete(target);
        return TodoDto.createTodoDto(target);
    }
}
