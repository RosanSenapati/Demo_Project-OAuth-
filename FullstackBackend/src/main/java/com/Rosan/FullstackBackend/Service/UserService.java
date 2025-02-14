package com.Rosan.FullstackBackend.Service;

import com.Rosan.FullstackBackend.DTO.UserDTO;
import com.Rosan.FullstackBackend.exception.EmailAlreadyExistsException;
import com.Rosan.FullstackBackend.exception.UserNotFoundException;
import com.Rosan.FullstackBackend.model.User;
import com.Rosan.FullstackBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Create New User (Sign Up)
    public UserDTO createUser(User user) {
        // Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Get All Users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get User by ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToDTO(user);
    }

    // User Login
    public UserDTO loginUser(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && passwordEncoder.matches(password, existingUser.get().getPassword())) {
            return convertToDTO(existingUser.get());
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    // Convert User Entity to DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
