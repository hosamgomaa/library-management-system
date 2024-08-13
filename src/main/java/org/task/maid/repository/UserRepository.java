package org.task.maid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.task.maid.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
