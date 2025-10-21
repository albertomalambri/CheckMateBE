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
    public void inizializzazioneGamestate()
    {
        ScacchieraGamestate scacchiera = new ScacchieraGamestate();
        for (String column : colonne) {
            for (int i = 0; i < 8; i++) {
                Casella casella = new Casella();
                casella.setPosition(new Position(i,colonne.indexOf(column)));
                casella.setColor(controllaColoreCasella(colonne.indexOf(column),i));

                if (i<=1 || i>=6) {
                    if (i==1 || i==6) {
                        Pawn pedone = new Pawn();
                        pedone.setInGame(inGame.YES);
                        pedone.setPosizione(new Position(i,colonne.indexOf(column)));
                        pedone.setColor(controlloColorePezzo(i));
                    }
                    else {
                        switch (column) {
                            case "a", "h" -> {
                                Rook rook = new Rook();
                                rook.setInGame(inGame.YES);
                                rook.setPosizione(new Position(i,colonne.indexOf(column)));
                                rook.setColor(controlloColorePezzo(i));
                            }
                            case "b","g" -> {
                                Knight knight = new Knight();
                                knight.setInGame(inGame.YES);
                                knight.setPosizione(new Position(i,colonne.indexOf(column)));
                                knight.setColor(controlloColorePezzo(i));
                            }
                            case "c","f" -> {
                                Bishop bishop = new Bishop();
                                bishop.setInGame(inGame.YES);
                                bishop.setPosizione(new Position(i,colonne.indexOf(column)));
                                bishop.setColor(controlloColorePezzo(i));
                            }
                            case "d" -> {
                                Queen queen = new Queen();
                                queen.setInGame(inGame.YES);
                                queen.setPosizione(new Position(i,colonne.indexOf(column)));
                                queen.setColor(controlloColorePezzo(i));
                            }
                            case "e" -> {
                                King king = new King();
                                king.setInGame(inGame.YES);
                                king.setPosizione(new Position(i,colonne.indexOf(column)));
                                king.setColor(controlloColorePezzo(i));
                            }
                        }
                    }
                }
            }
        }
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

    public void secondoGamestate() //prende come input ScacchieraGamestate,Move
    {

        //pezzo viene mangiato?
        //mossa fattibile o non fattibile
    }
}
