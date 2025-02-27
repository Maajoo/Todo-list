package todo.todo.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import todo.todo.domain.Todo;
import todo.todo.domain.TodoItem;
import todo.todo.domain.TodoItemRepository;
import todo.todo.domain.TodoRepository;

@Controller
public class TodoItemController {
    @Autowired
    private TodoItemRepository todoitemRepository;
    @Autowired
    private TodoRepository todoRepository;

    // Selected todo page
    @RequestMapping("/open/{id}")
    public String giveTodolist(@PathVariable("id") Long todoId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("todoitems", todoitemRepository.findAllByTodoId(todoId));

        // Retrieve the todo list by id
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id: " +
                        todoId));

        // Retrieve and pass the list of todo items associated with the selected todo
        // list
        List<TodoItem> todoItems = todoitemRepository.findAllByTodoId(todoId);
        model.addAttribute("todo", todo);
        model.addAttribute("todoItems", todoItems);

        return "selectedtodo";
    }

    // Save Todoitem
    @RequestMapping(value = "/saveitem/{todoId}", method = RequestMethod.POST)
    public String saveItem(@PathVariable("todoId") Long todoId, TodoItem todoItem) {

        // Set the todo list id for the todo item
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id: " + todoId));
        todoItem.setTodo(todo);
        todoitemRepository.save(todoItem);

        // Redirect to the selectedtodo page for the current todo list
        return "redirect:/open/" + todoId;
    }

    // Delete todo item
    @DeleteMapping("/deleteItem/{todoItemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long todoItemId) {
        todoitemRepository.deleteById(todoItemId);

        return ResponseEntity.ok().build();
    }

    // Toggle todo item status
    @PostMapping("/toggleStatus/{todoItemId}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long todoItemId, @RequestBody Map<String, Boolean> statusMap) {
        boolean status = statusMap.get("status");
        TodoItem todoItem = todoitemRepository.findById(todoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo item Id: " + todoItemId));
        todoItem.setStatus(status);
        todoitemRepository.save(todoItem);

        return ResponseEntity.ok().build();
    }

    // RESTful service to get all todo items
    @RequestMapping(value = "/todoitems", method = RequestMethod.GET)
    public @ResponseBody List<TodoItem> todoListRest() {

        return (List<TodoItem>) todoitemRepository.findAll();
    }

    // RESTful service to get a todo item by id
    @RequestMapping(value = "/todoitem/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<TodoItem> findTodoRest(@PathVariable("id") Long todoitemId) {

        return todoitemRepository.findById(todoitemId);
    }
}
