package devxplorer.task_manager_api.config;

import devxplorer.task_manager_api.security.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Test
    void passwordEncoder_returnsBCryptPasswordEncoder() {
        SecurityConfig config = new SecurityConfig(Mockito.mock(JwtAuthFilter.class));
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("rawPassword", encoder.encode("rawPassword")));
    }

    @Test
    void authenticationManager_returnsFromConfig() throws Exception {
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        when(config.getAuthenticationManager()).thenReturn(mockManager);

        SecurityConfig securityConfig = new SecurityConfig(Mockito.mock(JwtAuthFilter.class));
        AuthenticationManager result = securityConfig.authenticationManager(config);
        assertEquals(mockManager, result);
    }
}