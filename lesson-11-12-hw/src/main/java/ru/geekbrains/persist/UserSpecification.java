package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public final class UserSpecification {

    public static Specification<User> usernameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("username"), name);
    }

    public static Specification<User> minAgeFilter(Integer minAge) {
        return (root, query, builder) -> builder.ge(root.get("age"), minAge);
    }

    public static Specification<User> maxAgeFilter(Integer maxAge) {
        return (root, query, builder) -> builder.le(root.get("age"), maxAge);
    }
}
