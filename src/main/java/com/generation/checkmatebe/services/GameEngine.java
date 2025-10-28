package com.generation.checkmatebe.services;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;

import java.util.Set;

import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEngine {
    @Autowired
    ScacchieraRepository repo;

    @Autowired
    GameStateService gameStateService;


    public ScacchieraGamestate inizializzaGamestate()
    {
        return gameStateService.inizializzaGamestate();
    }

    public CasellaDTO secondoGamestate(Long id, MossaDTO dto) //prende come input ScacchieraGamestate,Move
    {
        ScacchieraGamestate gameState = repo.getReferenceById(id);
        gameState.cambioTurno();
        //pezzo viene mangiato?
        //mossa fattibile o non fattibile
        return new CasellaDTO();
    }
}

//public boolean isChecked (ScacchieraGamestate gamestate, Casella casella)
//    {
//        /**
//         * se controllando nella riga verticale e orizzontale del re e trovo una regina o torre avversaria: checked
//         * se controllando le diagonali passanti per la posizione del re e trovo una regina o un alfiere avversario come primo pezzo: checked
//         * se nelle due caselle diagonali "superiori" trovo un pedone: checked
//         * se possibili mosse dei cavalli avversari coincidono con la posizione del re: checked
//         * */
//
//
//
//return isAttackedByRookOrQueen(gamestate, casella)
//                || isAttackedByBishopOrQueen(gamestate, casella)
//                || isAttackedByPawn(gamestate, casella)
//                || isAttackedByKnight(gamestate, casella);
//
//    }
//    private boolean isAttackedByRookOrQueen(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//            //recupero la posizione
//            int r,c;
//            Casella[][] scacchiera= gamestate.getScacchiera();
//            r = checkCasella.getRow();
//            c = checkCasella.getColumn();
//
//            if(checkCasella.getPezzo()!=null) {
//                Color pieceColor = checkCasella.getColorePezzo();
//                //for per scorrere a destra
//                for(int j=checkCasella.getColumn()+1;j<8;j++)
//                    if(scacchiera[r][j].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//
//                // for per scorrere a sinistra
//                for(int j=checkCasella.getColumn()+1;j>=0;j--)
//                    if(scacchiera[r][j].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//                //for per scorrere a sopra
//                for(int i=checkCasella.getRow();i>=0;i--)
//                    if(scacchiera[i][c].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//                //for per scorrere a sotto
//                for(int i=checkCasella.getRow();i<8;i++)
//                    if(scacchiera[i][c].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//            }
//        return false;
//    }
//    private boolean isAttackedByBishopOrQueen(ScacchieraGamestate gamestate, Casella checkCasella){
//        //recupero la posizione
//        int r,c;
//        Casella[][] scacchiera= gamestate.getScacchiera();
//        r = checkCasella.getRow();
//        c = checkCasella.getColumn();
//
//        // controllo diagonale alto a destra
//        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
//            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//
//        // controllo diagonale alto a sinistra
//        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
//            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//
//        // controllo diagonale basso a destra
//        for(int i=checkCasella.getRow();i<8;i++)//for per righe
//            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//        // controllo diagonale basso a sinistra
//        for(int i=checkCasella.getRow();i<8;i++)//for per righe
//            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//        return false;
//    }
//
//    private boolean controllaPezzo(Pezzo p, Casella checkCasella, Set<String> attackingPieces)
//    {
//        return(p.getColor()!=checkCasella.getPezzo().getColor() && attackingPieces.contains(p.getNome()));
//    }
//
//
//
//    private boolean isAttackedByPawn(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//        return false;
//    }
//    private boolean isAttackedByKnight(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//        return false;
//    }
//
//    public boolean isCheckMated()
//    {
//        /**
//         * controllo caselle adiacenti is checked o occupate da altri pezzi
//         * controllo se nelle posibili mosse dei pezzi amici possono catturare il pezzo avversario
//         * controllo se pezzi amici si possono mettere nella traiettoria
//         * */
//        return false;
//
//    }
//}
