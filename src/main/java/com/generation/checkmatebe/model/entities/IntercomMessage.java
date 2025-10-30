package com.generation.checkmatebe.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class IntercomMessage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User mittente;

    @ManyToOne(fetch = FetchType.EAGER)
    private User destinatario;

    private LocalDateTime timestamp;
    private String titolo;
    private String content;
    private boolean letto;
    private boolean archiviato=false;


}

