package com.generation.checkmatebe.model.entities;


import com.generation.checkmatebe.dtos.MossaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Partita
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPartita;
    private String giocatoreBianco;
    private String giocatoreNero;
    private LocalDateTime tempo;

    private String risultato; // "1-0", "0-1", "½-½"
    private List<Mossa> mosse;
    private String statoFinaleFEN;

    @ManyToOne(fetch = FetchType.EAGER)
    private User game;

    @OneToOne(mappedBy = "chessboard", fetch = FetchType.EAGER,orphanRemoval = true)
    private ScacchieraGamestate gamestate;


}
