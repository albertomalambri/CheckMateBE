package com.generation.checkmatebe.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// PartitaDTO.java
@Getter
@Setter
public class PartitaDTO
{
    private Long id;
    private String giocatoreBianco;
    private String giocatoreNero;
    private String risultato; // "1-0", "0-1", "½-½"
    private List<MossaDTO> mosse;
    private String statoFinaleFEN;
}

