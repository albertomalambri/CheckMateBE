package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.enums.Pezzo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Mossa implements Serializable
{
    private int turno;
    private Casella start; // "e2"
    private Casella end;  // "e4" -> [4] [3]
    private Pezzo pezzo; // "pedone", "cavallo", ecc. //ho modificato il tipo in Piece tj
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}
