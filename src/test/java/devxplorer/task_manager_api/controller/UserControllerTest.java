package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock UserService userService;
    @InjectMocks UserController userController;

    @Mock Authentication authentication;
    @Mock SecurityContext securityContext;

    @Test
    void getAllUsers_returnsUserList() {
        List<UserDTO> users = List.of(new UserDTO());
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();
        assertEquals(users, response.getBody());
    }

    @Test
    void getUserById_found() {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setUsername("john");
        userDto.setEmail("john@example.com");

        when(userService.getUserById(1L)).thenReturn(Optional.of(userDto));
        ResponseEntity<UserDTO> response = userController.getUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john", response.getBody().getUsername());
    }

    @Test
    void getUserById_notFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        ResponseEntity<UserDTO> response = userController.getUserById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getCurrentUser_found() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@example.com");

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john");
        when(userService.getUserByUsername("john")).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> response = userController.getCurrentUser();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john", response.getBody().getUsername());
    }

    @Test
    void getCurrentUser_notFound() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john");
        when(userService.getUserByUsername("john")).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getCurrentUser();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_noContent() {
        when(userService.deleteUser(1L)).thenReturn(true);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteUser_notFound() {
        when(userService.deleteUser(1L)).thenReturn(false);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}