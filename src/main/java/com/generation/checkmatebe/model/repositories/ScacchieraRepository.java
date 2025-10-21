package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScacchieraRepository extends JpaRepository<ScacchieraGamestate, Long> {
}
