package devxplorer.task_manager_api.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secret = "ZmFrZXNlY3JldGtleWZvcnRlc3Rpbmdvbmx5ZmFrZXNlY3JldGtleQ=="; // base64 for "fakesecretkeyfortestingonlyfakesecretkey"
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "SECRET", secret);

        userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testuser")
        ;
        Mockito.when(userDetails.getAuthorities())
                .thenReturn((Collection) List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void generateAndValidateToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        assertEquals("testuser", jwtUtil.extractUsername(token));
        assertEquals("ROLE_USER", jwtUtil.extractRole(token));
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void validateToken_invalidUsername_returnsFalse() {
        String token = jwtUtil.generateToken(userDetails);

        UserDetails otherUser = Mockito.mock(UserDetails.class);
        Mockito.when(otherUser.getUsername()).thenReturn("otheruser");

        assertFalse(jwtUtil.validateToken(token, otherUser));
    }

    @Test
    void extractUsername_invalidToken_throws() {
        assertThrows(Exception.class, () -> jwtUtil.extractUsername("fake.invalid.token"));
    }
}