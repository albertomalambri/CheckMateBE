package com.generation.checkmatebe.dtos;

import com.generation.checkmatebe.model.entities.Mossa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieceDTO
{
//    private int numero;
//    private String tipo;       // "pedone", "torre", ecc.
//    private String colore;     // "BIANCO" o "NERO"
    private String posizione;  // "e2", "g8", ecc.
    private List<String> mossePossibili;
}

