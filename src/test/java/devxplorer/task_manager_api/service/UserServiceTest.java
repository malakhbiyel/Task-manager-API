package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserService userService;

    @Test
    void createUser_success() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("john");
        dto.setEmail("john@example.com");
        dto.setPassword("password");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDTO userDTO = userService.createUser(dto);

        assertEquals("john", userDTO.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_usernameExists_throwsException() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_emailExists_throwsException() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("john");
        dto.setEmail("john@example.com");
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
    }

    @Test
    void getAllUsers_returnsUserDTOs() {
        User user = new User();
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(1, result.size());
    }

    @Test
    void getUserById_and_getUserByUsername_work() {
        User user = new User(); user.setId(1L); user.setUsername("john");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        assertTrue(userService.getUserById(1L).isPresent());
        assertTrue(userService.getUserByUsername("john").isPresent());
    }

    @Test
    void deleteUser_whenExists_returnsTrue() {
        when(userRepository.existsById(1L)).thenReturn(true);
        boolean result = userService.deleteUser(1L);
        assertTrue(result);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_whenNotExists_returnsFalse() {
        when(userRepository.existsById(2L)).thenReturn(false);
        assertFalse(userService.deleteUser(2L));
    }
}