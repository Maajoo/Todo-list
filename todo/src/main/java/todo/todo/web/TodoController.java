package todo.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import todo.todo.domain.Todo;
import todo.todo.domain.TodoItem;
// import todo.todo.domain.TodoItem;
import todo.todo.domain.TodoItemRepository;
import todo.todo.domain.TodoRepository;

// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoItemRepository todoitemRepository;

    // private List<TodoItem> todoItems = new ArrayList<>();

    // Login page
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    //

    //

    //

    // Todo list page
    @RequestMapping("/todolist")
    public String giveTodolist(Model model) {
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

    // Open Todo
    @RequestMapping(value = "/open/{id}", method = RequestMethod.GET)
    public String openTodo(@PathVariable("id") Long todoId, Model model) {
        // Retrieve the todo list
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id: " + todoId));

        // Retrieve and pass the list of todo items associated with the todo list
        List<TodoItem> todoItems = todoitemRepository.findAllByTodoId(todoId);

        // Add the todo list and todo items to the model
        model.addAttribute("todo", todo);
        model.addAttribute("todoItems", todoItems);

        // Return the selectedtodo.html template
        return "selectedtodo";
    }

    //

    //

    //

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

    //

    //

    //

    // // Save Todoitem
    // @RequestMapping(value = "/saveitem", method = RequestMethod.POST)
    // public String saveitem(TodoItem todoitem) {
    // todoitemRepository.save(todoitem);
    // return "redirect:/open/{id}";
    // }

    // Add Todo Item
    // @PostMapping("/addTodoItem")
    // public String addTodoItem(@ModelAttribute TodoItem todoItem, Model model) {
    // todoItems.add(todoItem);
    // model.addAttribute("todoItem", new TodoItem());
    // model.addAttribute("todoItems", todoItems);
    // return "selectedtodo";
    // }

    // // Selected todo page
    // @GetMapping("/selectedtodo")
    // public String todoItemForm(Model model) {
    // model.addAttribute("todoItem", new TodoItem());
    // model.addAttribute("todoItems", todoItems);
    // return "selectedtodo";
    // }

    // // Update Todo Status
    // @PostMapping("/updateTodo/{todoItemId}")
    // @ResponseBody
    // public String updateTodo(@PathVariable("todoItemId") long todoItemId) {
    // Optional<TodoItem> todoToUpdate = todoItems.stream()
    // .filter(t -> t.getId() == todoItemId)
    // .findFirst();

    // todoToUpdate.ifPresent(todoItem -> todoItem.setDone(!todoItem.isDone()));
    // return todoToUpdate.map(todoItem -> Boolean.toString(todoItem.isDone()))
    // .orElse("error");
    // }
}