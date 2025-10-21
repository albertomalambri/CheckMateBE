package com.generation.checkmatebe.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mossa {

    private int numero;
    private String da; // "e2"
    private String a;  // "e4"
    private String pezzo; // "pedone", "cavallo", ecc.
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}
