package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.model.Role;
import devxplorer.task_manager_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_whenUserExists_returnsUserDetails() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("encodedpassword");
        user.setRole(Role.USER);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername("john");

        assertEquals("john", userDetails.getUsername());
        assertEquals("encodedpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_whenUserNotFound_throwsException() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("notfound"));
    }
}