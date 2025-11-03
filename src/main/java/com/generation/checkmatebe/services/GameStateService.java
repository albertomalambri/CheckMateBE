package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PartitaDTO;
import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.*;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.model.repositories.PartitaRepo;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.model.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameStateService
{
    @Autowired
    private ScacchieraRepository repo;

    @Autowired
    private PartitaRepo pRepo;

    @Autowired
    private UserRepository uRepo;

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
    public ScacchieraGamestateDTO inizializzaGamestate(Long id)
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
        gameState.setChessboard(pRepo.getReferenceById(id));
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
        dto.setStallo(gamestate.isStallo());
        return dto;
    }

    public PartitaDTO fineGamestate(User user, Long id) {
        Optional<ScacchieraGamestate> gamestate = repo.findById(id);
        Partita game;
        if (gamestate.isPresent()) {
            game = user.getPartite().stream().filter(partita -> partita == pRepo.findPartitaById(gamestate.get().getChessboard().getId())).toList().get(0);
            game.setMosse(gamestate.get().getPreviousMoves());
            if (gamestate.get().getCurrentPlayer() == Color.NERO)
                game.setRisultato(game.getGiocatoreBianco());
            else
                game.setRisultato(game.getGiocatoreNero());
            return convertPartitaToDto(game);
        }
        else
            return null;


    }

    private PartitaDTO convertPartitaToDto(Partita game) {
        PartitaDTO dto = new PartitaDTO();
        dto.setId(game.getId());
        dto.setGiocatoreBianco(game.getGiocatoreBianco());
        dto.setGiocatoreNero(game.getGiocatoreNero());
        dto.setRisultato(game.getRisultato());
        dto.setMosse(convertiMosseDto(game.getGamestate().getPreviousMoves()));
//        dto.setStatoFinaleFEN();
        return dto;
    }

    private List<MossaDTO> convertiMosseDto(LinkedList<Mossa> previousMoves) {
        List<MossaDTO> lista = new ArrayList<>();
        for (Mossa m : previousMoves) {
            MossaDTO dto = new MossaDTO();
            dto.setNumero(m.getTurno());
            dto.setDa(m.getStart().getNomeCasella());
            dto.setA(m.getEnd().getNomeCasella());
            dto.setPezzo(m.getPezzo().getCodice());
            lista.add(dto);
        }
        return lista;
    }

//    private int numero;
//    private String da; // "e2"
//    private String a;  // "e4"
//    private String pezzo; // "pedone", "cavallo", ecc.
//    private boolean cattura;
//    private boolean arrocco;
//    private boolean promozione;

}
