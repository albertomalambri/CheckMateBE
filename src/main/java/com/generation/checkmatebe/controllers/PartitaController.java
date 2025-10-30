package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.services.GameEngineService;
import com.generation.checkmatebe.services.GameStateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partita")
public class PartitaController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private GameEngineService gameEngineService;

    @PostMapping("/start")
    public ResponseEntity<ScacchieraGamestateDTO> startGame() {
        ScacchieraGamestateDTO risultatoDTO = gameStateService.inizializzaGamestate();
        return ResponseEntity.ok(risultatoDTO);
    }


    @GetMapping("/stato/{id}")
    public ResponseEntity<ScacchieraGamestateDTO> getStatoScacchiera(@PathVariable Long id) {
        try {
            ScacchieraGamestateDTO risultatoDTO = new ScacchieraGamestateDTO();
            return ResponseEntity.ok(risultatoDTO);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mossa/{id}")
    public ResponseEntity<ScacchieraGamestateDTO> eseguiMossa(@PathVariable Long id, @RequestBody MossaDTO mossa) {
        try {
            ScacchieraGamestate risultato = gameEngineService.nextGameState(id, mossa);// <-- conversione necessaria
            ScacchieraGamestateDTO risultatoDTO = gameStateService.convertToDtoScacchiera(risultato);
            return ResponseEntity.ok(risultatoDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

