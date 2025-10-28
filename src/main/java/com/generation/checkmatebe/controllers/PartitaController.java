package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.services.GameEngine;
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
    private GameEngine gameEngine;

    @PostMapping("/start")
    public ResponseEntity<List<CasellaDTO>> startGame() {
        var gameState = gameStateService.inizializzaGamestate();
        var caselle = gameStateService.findAllAsDto(gameState.getId());
        return ResponseEntity.ok(caselle);
    }

    @GetMapping("/stato/{id}")
    public ResponseEntity<List<CasellaDTO>> getStatoScacchiera(@PathVariable Long id) {
        try {
            var caselle = gameStateService.findAllAsDto(id);
            return ResponseEntity.ok(caselle);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mossa/{id}")
    public ResponseEntity<CasellaDTO> eseguiMossa(@PathVariable Long id, @RequestBody MossaDTO mossa) {
        try {
            var risultato = gameEngine.secondoGamestate(id, mossa);
            return ResponseEntity.ok(risultato);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
