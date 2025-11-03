package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PartitaDTO;
import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.entities.User;
import com.generation.checkmatebe.model.repositories.UserRepository;
import com.generation.checkmatebe.services.GameEngineService;
import com.generation.checkmatebe.services.GameStateService;
import com.generation.checkmatebe.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/partita")
public class PartitaController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private GameEngineService gameEngineService;

    @Autowired
    private UserService userv;

    @PostMapping("/start")
    public ResponseEntity<ScacchieraGamestateDTO> startGame(HttpServletRequest request) {
        try {
//            if (token == null)
//                return null;
            Optional<User> users = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equalsIgnoreCase("token")).map(token -> userv.findUserByToken(token.getValue())).findFirst();
            if (users.isEmpty())
                return null;
            ScacchieraGamestateDTO risultatoDTO = gameEngineService.inizializzaGamestate(users.get().getUsername());
            return ResponseEntity.ok(risultatoDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/stato/{id}")
    public ResponseEntity<PartitaDTO> getStatoScacchieraFineGame(@PathVariable Long id, HttpServletRequest request) {
        try {

            Optional<User> users = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equalsIgnoreCase("token")).map(token -> userv.findUserByToken(token.getValue())).findFirst();
            if (users.isEmpty())
                return null;
            PartitaDTO risultatoDTO = gameStateService.fineGamestate(users.get(), id);
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
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

