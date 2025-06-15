package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.security.JwtUtil;
import devxplorer.task_manager_api.service.CustomUserDetailsService;
import devxplorer.task_manager_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock AuthenticationManager authenticationManager;
    @Mock CustomUserDetailsService userDetailsService;
    @Mock JwtUtil jwtUtil;
    @Mock UserService userService;

    @InjectMocks AuthController authController;

    @Test
    void login_returnsTokenOnSuccess() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("john");
        dto.setPassword("pass");
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("john")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("myToken");

        ResponseEntity<?> response = authController.login(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((Map<?, ?>)response.getBody()).containsKey("token"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void register_returnsCreatedUser() {
        UserCreateDTO dto = new UserCreateDTO();
        UserDTO userDTO = new UserDTO();
        when(userService.createUser(dto)).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = authController.register(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }
}