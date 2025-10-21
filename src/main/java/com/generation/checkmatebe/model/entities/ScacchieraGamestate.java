package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.enums.Color;
import com.generation.checkmatebe.model.Position;
import com.generation.checkmatebe.model.entities.pieces.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ScacchieraGamestate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<Position> piecesPosition;


    @NotNull
    @Enumerated(EnumType.STRING)
    private Color currentPlayer = Color.BIANCO; // Bianco comincia


    private List<String> previousMoves;


    private List<Casella> caselle;


    private List<Piece> pezzi;



    public void refreshPosizioni() {
        List<Position> posizioni = new ArrayList<>();
        for (Piece p : pezzi) {
            posizioni.add(p.getPosizione());
        }
        this.piecesPosition = posizioni;
    }
}
