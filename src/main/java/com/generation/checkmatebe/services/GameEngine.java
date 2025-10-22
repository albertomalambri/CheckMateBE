package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PieceDTO;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.inGame;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.entities.pieces.*;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.utilities.ChessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEngine
{
    @Autowired
    ScacchieraRepository repo;

    public ScacchieraGamestate inizializzaGamestate()
    {
        ScacchieraGamestate scacchiera = new ScacchieraGamestate();
        Casella[][] caselle = new Casella[8][8];
        for (int i = 0; i < 8; i++) { //righe
            for (int j = 0; j < 8; j++) { //colonne
                Casella casella = new Casella(i,j);
                casella.setColor(controllaColoreCasella(j,i));
                casella.setGameState(scacchiera);
                caselle[i][j]=casella;

                if (i<=1 || i>=6) {
                    if (i==1 || i==6) {
                        Pawn pedone = new Pawn();
                        pedone.setInGame(inGame.YES);
                        pedone.setPosizione(casella);
                        pedone.setColor(controlloColorePezzo(i));
                    }
                    else {
                        switch (""+ChessUtils.positionToString(i,j).charAt(0)) {
                            case "a", "h" -> {
                                Rook rook = new Rook();
                                rook.setInGame(inGame.YES);
                                rook.setPosizione(casella);
                                rook.setColor(controlloColorePezzo(i));
                            }
                            case "b","g" -> {
                                Knight knight = new Knight();
                                knight.setInGame(inGame.YES);
                                knight.setPosizione(casella);
                                knight.setColor(controlloColorePezzo(i));
                            }
                            case "c","f" -> {
                                Bishop bishop = new Bishop();
                                bishop.setInGame(inGame.YES);
                                bishop.setPosizione(casella);
                                bishop.setColor(controlloColorePezzo(i));
                            }
                            case "d" -> {
                                Queen queen = new Queen();
                                queen.setInGame(inGame.YES);
                                queen.setPosizione(casella);
                                queen.setColor(controlloColorePezzo(i));
                            }
                            case "e" -> {
                                King king = new King();
                                king.setInGame(inGame.YES);
                                king.setPosizione(casella);
                                king.setColor(controlloColorePezzo(i));
                            }
                        }
                    }
                }
            }
        }
        scacchiera.setScacchiera(caselle);

        return scacchiera;
    }

    private Color controlloColorePezzo(int row) {
        if (row<=1)
            return Color.BIANCO;
        else
            return Color.NERO;
    }

    private Color controllaColoreCasella(int column, int row) {
        if (column%2==1) {
            if (row%2==0)
                return Color.BIANCO;
            else
                return Color.NERO;
        }
        else {
            if (row%2==0)
                return Color.NERO;
            else
                return Color.BIANCO;
        }
    }

    public List<PieceDTO> findAllAsDto(Long id) {
        Casella[][] scacchiera = repo.getReferenceById(id).getScacchiera();
        List<PieceDTO> tuttiDto = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(scacchiera[i][j].getPezzo()!=null) {
                    List<String> pos = new ArrayList<>();
                    PieceDTO piece = new PieceDTO();
                    piece.setPosizione(scacchiera[i][j].getNomeCasella());
                    if(scacchiera[i][j].getPezzo().getColor()==repo.getReferenceById(id).getCurrentPlayer()) {
                        for (Casella c : scacchiera[i][j].getPezzo().calcolaMossePossibili())
                            pos.add(ChessUtils.positionToString(c.getNomeCasella().charAt(0), c.getNomeCasella().charAt(1)));
                        piece.setMossePossibili(pos);
                    }
                    else
                        pos = null;
                    tuttiDto.add(piece);
                }
            }

        }
        return tuttiDto;
    }

//    private Piece creaPezzoIniziale(int row, int col) {
//        // pedoni
//        if (row == 1)
//            return new Pawn();
//        if (row == 6)
//            return new Pawn();
//
//        // pezzi principali
//        if (row == 0 || row == 7) {
//            Color color = (row == 0) ? Color.BIANCO : Color.NERO;
//            return switch (col) {
//                case 0, 7 -> new Rook();
//                case 1, 6 -> new Knight();
//                case 2, 5 -> new Bishop();
//                case 3 -> new Queen();
//                case 4 -> new King();
//                default -> null;
//            };
//        }
//        return null;
//    }

    public PieceDTO secondoGamestate(Long id, MossaDTO dto) //prende come input ScacchieraGamestate,Move
    {
        ScacchieraGamestate gameState = repo.getReferenceById(id);
        gameState.cambioTurno();
        //pezzo viene mangiato?
        //mossa fattibile o non fattibile
        return new PieceDTO();
    }
}
