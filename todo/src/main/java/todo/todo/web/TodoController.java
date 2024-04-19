package todo.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import todo.todo.domain.Todo;
import todo.todo.domain.TodoRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    // Login page
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    // Register page
    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    // Todo list page
    @RequestMapping("/todolist")
    public String giveTodolist(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("todos", todoRepository.findAll());
        return "todolist";
    }

    // Save Todo
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todolist";
    }

    // Delete todo list
    @DeleteMapping("/deleteList/{todoId}")
    public ResponseEntity<?> deleteList(@PathVariable Long todoId) {
        todoRepository.deleteById(todoId);
        return ResponseEntity.ok().build();
    }


    // RESTful service to get all todos
    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public @ResponseBody List<Todo> todoListRest() {
        return (List<Todo>) todoRepository.findAll();
    }

    // RESTful service to get a todo by id
    @SuppressWarnings("null")
    @RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Todo> findBookRest(@PathVariable("id") Long todoId) {
        return todoRepository.findById(todoId);
    }
}