package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartitaRepo extends JpaRepository<Partita,Long> {

    Partita findPartitaById(Long id);
}
