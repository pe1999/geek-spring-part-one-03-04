package ru.geekbrains.persist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

    private Long id;

    @Size(min = 3, message = "Username should be longer then 3")
    @NotBlank(message = "Username shouldn't be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{0,}$", message = "Not correct symbols")
    private String username;

    @Size(min = 3, message = "Password should be longer then 3")
    @NotBlank(message = "Password shouldn't be blank")
    private String password;

    private String passwordToConfirm;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.passwordToConfirm = password;
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

    public String getPasswordToConfirm() {
        return passwordToConfirm;
    }

    public void setPasswordToConfirm(String passwordToConfirm) {
        this.passwordToConfirm = passwordToConfirm;
    }
}
