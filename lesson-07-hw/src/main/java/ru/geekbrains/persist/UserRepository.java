package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsernameLike(String username);

    List<User> findAllByAgeBetween(Integer min, Integer max);

    User findByUsername(String username);
}
