package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.mapper.UserMapper;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserCreateDTO dto) {
        User user = UserMapper.fromCreateDTO(dto);
        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }
}
