package todo.todo.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import todo.todo.domain.User;
import todo.todo.domain.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the repository based on the provided username
        User currentUser = repository.findByUsername(username);

        // Check if the user exists
        if (currentUser == null) {
            // If user not found, throw UsernameNotFoundException
            throw new UsernameNotFoundException("Could not find user with username: " + username);
        }

        // Prepare user roles
        String[] roles = currentUser.getRole().startsWith("ROLE_") ? new String[] { currentUser.getRole() }
                : new String[] { "ROLE_" + currentUser.getRole() };

        // Create UserDetails object representing the authenticated user
        UserDetails user = new org.springframework.security.core.userdetails.User(username,
                currentUser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(roles));
        return user;
    }

}
