package todo.todo;

//import static method antMatcher
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    // with lambda
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(antMatcher("/css/**")).permitAll() // Enable css when logged out
                .requestMatchers(antMatcher("/add-todo-item")).permitAll() // Allow access to add-todo-item for ADMIN and USER roles
                .requestMatchers(antMatcher("/h2-console/**")).permitAll() // Allow access to H2 console
                .anyRequest().authenticated()
            )
            .formLogin(formlogin -> formlogin
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/todolist", true).permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );

        // Allow access to H2 console with CSRF disabled
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
