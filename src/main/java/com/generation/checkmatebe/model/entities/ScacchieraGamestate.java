package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.entities.pieces.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;

@Entity
@Getter
@Setter
public class ScacchieraGamestate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Casella[][] scacchiera= new Casella[8][8];

    @NotNull
    @Enumerated(EnumType.STRING)
    private Color currentPlayer = Color.BIANCO; // Bianco comincia
    private LinkedList<Mossa> previousMoves; // linked list per tenerle in ordine
    private boolean isCheck; //controllo Check
    private boolean isCheckMate; //controllo checkMate
    //private boolean live; //partita live o finita




//    public void refreshPosizioni() {
//        Casella[][] posizioni = new Casella[8][8];
//        for (int i = 0; i < 7; i++) {
//            for (int j = 0; j < 7; j++) {
//                posizioni[i][j]= ;
//        }
//        this.scacchiera = posizioni;
//    }
}
