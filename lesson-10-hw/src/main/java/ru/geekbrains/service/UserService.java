package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.FilterDto;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.UserSpecification;

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
    public UserDto save(UserDto user) {
        User saved = userRepository.save(convertToDao(user));
        user.setId(saved.getId());
        return user;
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

    public Page<UserDto> findWithFilter(FilterDto filterDto) {
        String usernameFilter = filterDto.getUsernameFilter() != null && filterDto.getUsernameFilter().isBlank() ? null
                : filterDto.getUsernameFilter();
        Pageable request = PageRequest.of(filterDto.getPage() == null ? 0 : filterDto.getPage() - 1,
                filterDto.getSize() == null ? 4 : filterDto.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));
        return userRepository.findWithFilter(usernameFilter,
                filterDto.getMinAge(),
                filterDto.getMaxAge(),
                request
        ).map(UserService::convertToDto);
    }

    public Page<UserDto> findWithFilterSpec(FilterDto filterDto) {
        Specification<User> spec = Specification.where(null);
        if (filterDto.getUsernameFilter() != null && !filterDto.getUsernameFilter().isBlank()) {
            spec = spec.and(UserSpecification.usernameLike(filterDto.getUsernameFilter() + "%"));
        }
        if (filterDto.getMinAge() != null) {
            spec = spec.and(UserSpecification.minAgeFilter(filterDto.getMinAge()));
        }
        if (filterDto.getMaxAge() != null) {
            spec = spec.and(UserSpecification.maxAgeFilter(filterDto.getMaxAge()));
        }

        String sortColumn = filterDto.getSortColumn() == null || filterDto.getSortColumn().isBlank() ? "id" : filterDto.getSortColumn();
        PageRequest request = PageRequest.of(
                filterDto.getPage() == null ? 0 : filterDto.getPage() - 1,
                filterDto.getSize() == null ? 4 : filterDto.getSize(),
                Sort.by(Sort.Direction.ASC, sortColumn)
        );
        return userRepository.findAll(spec, request)
                .map(UserService::convertToDto);
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
