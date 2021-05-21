package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CartEntryRepository extends JpaRepository<CartEntry, Long>, JpaSpecificationExecutor<CartEntry> {

    List<CartEntry> findByUserId(Long user_id);

    void deleteCartEntryByUserId(Long user_id);

}
