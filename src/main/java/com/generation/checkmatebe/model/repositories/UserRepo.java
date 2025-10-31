package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>
{

    User findByUsername(String username);
}
