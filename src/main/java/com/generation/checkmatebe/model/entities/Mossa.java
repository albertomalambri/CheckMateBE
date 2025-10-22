package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.entities.pieces.Piece;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mossa
{
    private int numero;
    private String da; // "e2"
    private String a;  // "e4"
    private Piece pezzo; // "pedone", "cavallo", ecc. //ho modificato il tipo in Piece tj
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}
