package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.entities.pieces.Piece;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Mossa implements Serializable
{
    private int turno;
    private String da; // "e2"
    private String a;  // "e4"
    private Piece pezzo; // "pedone", "cavallo", ecc. //ho modificato il tipo in Piece tj
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}
