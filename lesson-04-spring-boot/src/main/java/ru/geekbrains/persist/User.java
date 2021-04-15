package ru.geekbrains.persist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class User {

    private Long id;

    @Size(min = 3, message = "Username should be longer then 3")
    @NotBlank(message = "Username shouldn't be blank")
    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
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
}
