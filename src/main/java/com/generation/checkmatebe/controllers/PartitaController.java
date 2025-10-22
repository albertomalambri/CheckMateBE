package com.generation.checkmatebe.controllers;

//commento
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PartitaDTO;
import com.generation.checkmatebe.dtos.PieceDTO;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.services.GameEngine;
import com.generation.checkmatebe.utilities.ChessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mock")
public class PartitaController
{
    @Autowired
    GameEngine gameEngine;

//    @GetMapping("/partita")
//    public ResponseEntity<PartitaDTO> getMockPartita()
//    {
//        PartitaDTO partita = new PartitaDTO();
//        partita.setId(1L);
//        partita.setGiocatoreBianco("Alberto");
//        partita.setGiocatoreNero("Stockfish");
//        partita.setRisultato("1-0");
//        partita.setStatoFinaleFEN("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2");
//
//        List<MossaDTO> mosse = new ArrayList<>();
//        mosse.add(new MossaDTO(1, "e2", "e4", "pedone", false, false, false));
//        mosse.add(new MossaDTO(1, "c7", "c5", "pedone", false, false, false));
//        partita.setMosse(mosse);
//
//        return ResponseEntity.ok(partita);
//    }

    @PostMapping("/start")
    public List<PieceDTO> startGame()
    {
        return gameEngine.findAllAsDto(gameEngine.inizializzaGamestate().getId());
    }
}

//    @GetMapping("/pezzi")
//    public ResponseEntity<List<PieceDTO>> getPezziCorrenti()
//    {
//        ScacchieraGamestate stato = gameEngine.inizializzaGamestate(); //scacchiera a inizio partita
//        List<PieceDTO> pezzi = stato.getPezzi().stream()
//                .map(ChessUtils::converti)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(pezzi);
//    }
//}

