package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.Partita;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScacchieraRepository extends JpaRepository<ScacchieraGamestate, Long> {
    ScacchieraGamestate findByChessboard(Partita chessboard);

//    ScacchieraGamestate findById(Long id);
    Optional<ScacchieraGamestate> findByChessboard_Id(Long partitaId);
}
