package com.generation.checkmatebe.services;

import com.generation.checkmatebe.enums.Color;
import com.generation.checkmatebe.enums.inGame;
import com.generation.checkmatebe.model.Position;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.entities.pieces.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEngine
{
    private List<String> colonne = List.of("a", "b", "c", "d","e","f","g","h");
    public ScacchieraGamestate inizializzazioneGamestate()
    {
        ScacchieraGamestate scacchiera = new ScacchieraGamestate();
        List<Piece> pezzi = new ArrayList<>();
        List<Casella> caselle = new ArrayList<>();
        for (String column : colonne) {
            for (int i = 0; i < 8; i++) {
                Casella casella = new Casella();
                casella.setPosition(new Position(i,colonne.indexOf(column)));
                casella.setColor(controllaColoreCasella(colonne.indexOf(column),i));
                caselle.add(casella);

                if (i<=1 || i>=6) {
                    if (i==1 || i==6) {
                        Pawn pedone = new Pawn();
                        pedone.setInGame(inGame.YES);
                        pedone.setPosizione(new Position(i,colonne.indexOf(column)));
                        pedone.setColor(controlloColorePezzo(i));
                        pezzi.add(pedone);
                    }
                    else {
                        switch (column) {
                            case "a", "h" -> {
                                Rook rook = new Rook();
                                rook.setInGame(inGame.YES);
                                rook.setPosizione(new Position(i,colonne.indexOf(column)));
                                rook.setColor(controlloColorePezzo(i));
                                pezzi.add(rook);
                            }
                            case "b","g" -> {
                                Knight knight = new Knight();
                                knight.setInGame(inGame.YES);
                                knight.setPosizione(new Position(i,colonne.indexOf(column)));
                                knight.setColor(controlloColorePezzo(i));
                                pezzi.add(knight);
                            }
                            case "c","f" -> {
                                Bishop bishop = new Bishop();
                                bishop.setInGame(inGame.YES);
                                bishop.setPosizione(new Position(i,colonne.indexOf(column)));
                                bishop.setColor(controlloColorePezzo(i));
                                pezzi.add(bishop);
                            }
                            case "d" -> {
                                Queen queen = new Queen();
                                queen.setInGame(inGame.YES);
                                queen.setPosizione(new Position(i,colonne.indexOf(column)));
                                queen.setColor(controlloColorePezzo(i));
                                pezzi.add(queen);
                            }
                            case "e" -> {
                                King king = new King();
                                king.setInGame(inGame.YES);
                                king.setPosizione(new Position(i,colonne.indexOf(column)));
                                king.setColor(controlloColorePezzo(i));
                                pezzi.add(king);
                            }
                        }
                    }
                }
            }
        }
        scacchiera.setCaselle(caselle);
        scacchiera.setPezzi(pezzi);
        scacchiera.refreshPosizioni();
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

    public void secondoGamestate() //prende come input ScacchieraGamestate,Move
    {

        //pezzo viene mangiato?
        //mossa fattibile o non fattibile
    }
}
