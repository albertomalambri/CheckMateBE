package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{
    Optional<User> findByToken(String token);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
