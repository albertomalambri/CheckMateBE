package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GameStateService
{
    @Autowired
    private ScacchieraRepository repo;

    public List<CasellaDTO> findAllAsDto(ScacchieraGamestate gameState) {
//        ScacchieraGamestate gameState = repo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("gamestate not found"));
        Casella[][] scacchiera = gameState.getScacchiera();
        List<CasellaDTO> caselleDTO = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++) {
                Casella casella = scacchiera[i][j];
                CasellaDTO dto = new CasellaDTO();
                dto.setRow(casella.getRow());
                dto.setColumn(casella.getColumn());
                dto.setNomeCasella(casella.getNomeCasella());
                dto.setPezzo(casella.getPezzo());
                dto.setColorePezzo(casella.getColorePezzo());
                caselleDTO.add(dto);
            }
        }
        return caselleDTO;
    }
    public ScacchieraGamestateDTO inizializzaGamestate()
    {
        ScacchieraGamestate gameState = new ScacchieraGamestate();
        Casella[][] scacchiera = new Casella[8][8];
        LinkedList<Mossa> previousMoves = new LinkedList<>();
        for (int i = 0; i < 8; i++) { //righe
            for (int j = 0; j < 8; j++) { //colonne
                Casella casella = new Casella(i, j);
                setupCasella(casella);
                scacchiera[i][j] = casella;
            }
        }
        gameState.setScacchiera(scacchiera);
        gameState.setPreviousMoves(previousMoves);
        repo.save(gameState);
        return convertToDtoScacchiera(gameState);
    }
    private void setupCasella(Casella casella)
    {
        int r = casella.getRow();
        int c = casella.getColumn();

        if (r == 1) {
            casella.setPezzo(Pezzo.PEDONE);
            casella.setColorePezzo(Color.NERO);
        } else if (r == 6) {
            casella.setPezzo(Pezzo.PEDONE);
            casella.setColorePezzo(Color.BIANCO);
        } else if (r == 0 || r == 7) {
            Color colore = (r == 0) ? Color.NERO : Color.BIANCO;
            Pezzo pezzo = switch (c) {
                case 0, 7 -> Pezzo.TORRE;
                case 1, 6 -> Pezzo.CAVALLO;
                case 2, 5 -> Pezzo.ALFIERE;
                case 3 -> Pezzo.REGINA;
                case 4 -> Pezzo.RE;
                default -> null;
            };
            casella.setPezzo(pezzo);
            casella.setColorePezzo(colore);
        } else
        {
            casella.svuotaCasella(); // pezzo = null
            casella.setColorePezzo(null);
        }
    }

    public ScacchieraGamestateDTO convertToDtoScacchiera(ScacchieraGamestate gamestate) {
        ScacchieraGamestateDTO dto = new ScacchieraGamestateDTO();
        dto.setId(gamestate.getId());
        dto.setScacchiera(findAllAsDto(gamestate));
        dto.setCurrentPlayer(gamestate.getCurrentPlayer());
        dto.setCheck(gamestate.isCheck());
        dto.setCheckMate(gamestate.isCheckMate());
        return dto;
    }
}
