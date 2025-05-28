package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.UserCreateDTO;
import devxplorer.task_manager_api.dto.UserDTO;
import devxplorer.task_manager_api.exception.ResourceNotFoundException;
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

        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        User user = UserMapper.fromCreateDTO(dto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
