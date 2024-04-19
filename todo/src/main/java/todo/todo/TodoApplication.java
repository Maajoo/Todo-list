package todo.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import todo.todo.domain.Todo;
import todo.todo.domain.TodoItemRepository;
import todo.todo.domain.TodoRepository;
import todo.todo.domain.User;
import todo.todo.domain.UserRepository;

@SpringBootApplication
public class TodoApplication {


    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, TodoRepository todoRepository, TodoItemRepository todoItemRepository) {
        return (args) -> {

			// Create todos
			Todo todo1 = new Todo("Testi todo 1");
			Todo todo2 = new Todo("Testi todo 2");
			Todo todo3 = new Todo("Testi todo 3");
			todoRepository.save(todo1);
			todoRepository.save(todo2);
			todoRepository.save(todo3);

            // Create users: admin/admin user/user
			User user1 = new User("user", "$2y$10$BFUV32D5y7IWNrDnIv7iX.UpGlkOaO4OXa16zhXkuD9PyQZTsDmpm", "user.user@email.com", "USER");
			User user2 = new User("admin", "$2y$10$Ink5PBDfLR/yWQ2zdnqG2OtmtZ5Sx4zrilOg3lIrGWeNhNZy/kp0G", "admin.admin@email.com", "ADMIN");
			User user3 = new User("mio", "$2y$10$dgN6OwQg1tBiAiwZmamYZe9b6KZ/NqM0ikvDwYUASl9iPpFzDj8tO", "mio.kantola@email.com", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
        };
    }
}
