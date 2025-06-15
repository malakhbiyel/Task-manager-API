package devxplorer.task_manager_api.mapper;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toDTO_returnsUserDTO_whenUserNotNull() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alice");
        user.setEmail("alice@example.com");

        UserDTO dto = UserMapper.toDTO(user);

        assertNotNull(dto);
        assertEquals("alice", dto.getUsername());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    void toDTO_returnsNull_whenUserIsNull() {
        assertNull(UserMapper.toDTO(null));
    }

    @Test
    void fromCreateDTO_setsFieldsAndRole_whenRoleIsValid() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("bob");
        dto.setEmail("bob@ex.com");
        dto.setPassword("pw");
        dto.setRole("ADMIN");

        User user = UserMapper.fromCreateDTO(dto);

        assertNotNull(user);
        assertEquals("bob", user.getUsername());
        assertEquals("bob@ex.com", user.getEmail());
        assertEquals("pw", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void fromCreateDTO_setsDefaultRole_whenRoleIsInvalid() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("eve");
        dto.setRole("notarole");

        User user = UserMapper.fromCreateDTO(dto);

        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void fromCreateDTO_setsDefaultRole_whenRoleIsNullOrBlank() {
        UserCreateDTO dto1 = new UserCreateDTO();
        dto1.setUsername("bob");
        dto1.setRole(null);

        User user1 = UserMapper.fromCreateDTO(dto1);
        assertEquals(Role.USER, user1.getRole());

        UserCreateDTO dto2 = new UserCreateDTO();
        dto2.setUsername("bob");
        dto2.setRole("");

        User user2 = UserMapper.fromCreateDTO(dto2);
        assertEquals(Role.USER, user2.getRole());
    }

    @Test
    void fromCreateDTO_returnsNull_whenDTONull() {
        assertNull(UserMapper.fromCreateDTO(null));
    }
}