package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDto {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "not correct symbols in username")
    @NotBlank(message = "Username shouldn't be blank")
    private String username;

    @NotBlank(message = "Password shouldn't be blank")
    private String password;

    @NotBlank(message = "Matching password shouldn't be blank")
    @JsonIgnore
    private String matchingPassword;

    @NotNull
    @Min(18)
    private Integer age;

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
