package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserService::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id)
                .map(UserService::convertToDto)
                .map(UserService::clearPassword);
    }

    @Transactional
    public void save(UserDto user) {
        userRepository.save(convertToDao(user));
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> findAllByUsername(String usernameFilter) {
        return userRepository.findAllByUsernameLike("%" + usernameFilter + "%").stream()
                .map(UserService::convertToDto)
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getAge());
    }

    private static User convertToDao(UserDto userDto) {
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getAge());
    }

    private static UserDto clearPassword(UserDto userDto) {
        userDto.setPassword("");
        userDto.setMatchingPassword("");
        return userDto;
    }
}
