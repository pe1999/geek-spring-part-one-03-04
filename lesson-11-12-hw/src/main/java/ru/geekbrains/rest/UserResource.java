package ru.geekbrains.rest;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.BadRequestException;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.dto.FilterDto;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8080/spring-mvc-app/swagger-ui/index.html
// http://localhost:8080/spring-mvc-app/v3/api-docs/

@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/v1/user")
@RestController
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .peek(u -> u.setPassword(null))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public Page<UserDto> filterUsers(@ParameterObject FilterDto filterDto) {
        return userService.findWithFilterSpec(filterDto)
                .map(usr -> {
                    usr.setPassword(null);
                    return usr;
                });
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public UserDto findById(@PathVariable("id") Long id) {
        UserDto userDto = userService.findById(id)
                .orElseThrow(NotFoundException::new);
        userDto.setPassword(null);
        return userDto;
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public UserDto create(@RequestBody UserDto userDto) {
        if (userDto.getId() != null) {
            throw new BadRequestException();
        }
        return userService.save(userDto);
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PutMapping(consumes = "application/json")
    public void update(@RequestBody UserDto userDto) {
        if (userDto.getId() == null) {
            throw new BadRequestException();
        }
        userService.save(userDto);
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
