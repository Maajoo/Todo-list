package todo.todo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    List<TodoItem> findAllByTodoId(Long todoId);
}
