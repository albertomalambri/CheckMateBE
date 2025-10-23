package com.generation.checkmatebe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// MossaDTO.java
public class MossaDTO
{
    private int turno;
    private String da; // "e2"
    private String a;  // "e4"
    private String pezzo; // "pedone", "cavallo", ecc.
    private boolean cattura;
    private boolean arrocco;
    private boolean promozione;
}

