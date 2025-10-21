package com.generation.checkmatebe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieceDTO
{
    private String tipo;       // "pedone", "torre", ecc.
    private String colore;     // "BIANCO" o "NERO"
    private String posizione;  // "e2", "g8", ecc.
}

