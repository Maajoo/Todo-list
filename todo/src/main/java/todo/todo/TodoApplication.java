package todo.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            // Create users: admin/admin user/user quest/quest
			User user1 = new User("user", "$2y$10$BFUV32D5y7IWNrDnIv7iX.UpGlkOaO4OXa16zhXkuD9PyQZTsDmpm", "user.user@email.com", "USER");
			User user2 = new User("admin", "$2y$10$Ink5PBDfLR/yWQ2zdnqG2OtmtZ5Sx4zrilOg3lIrGWeNhNZy/kp0G", "admin.admin@email.com", "ADMIN");
            User user3 = new User("quest", "$2y$10$1gZJyL5/kyW3RCUHAXgUl.D9THZTACPsSui8feFxl58uYLYQm6xRi", "quest.quest@email.com", "QUEST");
			userRepository.save(user1);
			userRepository.save(user2);
            userRepository.save(user3);
        };
    }
}
