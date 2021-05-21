package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findAllByUsernameLike(String username);

    List<User> findAllByAgeBetween(Integer min, Integer max);

    @Query("select u from User u " +
            "where (u.username like :usernameFilter or :usernameFilter is null) and " +
            "(u.age >= :minAge or :minAge is null) and " +
            "(u.age <= :maxAge or :maxAge is null)"
    )
    Page<User> findWithFilter(String usernameFilter, Integer minAge, Integer maxAge, Pageable pageable);
}
