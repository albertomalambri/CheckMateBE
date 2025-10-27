package com.generation.checkmatebe.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Mossa implements Serializable
{
    private int turno;
    private String start; // "e2"
    private String end;  // "e4" -> [4] [3]
    private Piece pezzo; // "pedone", "cavallo", ecc. //ho modificato il tipo in Piece tj
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}
