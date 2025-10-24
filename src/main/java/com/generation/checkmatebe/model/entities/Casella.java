package com.generation.checkmatebe.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.checkmatebe.model.entities.pieces.Piece;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Casella implements Serializable
{
    private final int row;     // da 1 a 8
    private final int column;
    // da 1 a 8 (oppure 'a'–'h' se vuoi usare lettere)
    private String nomeCasella; //es e4

    @JsonIgnore
    private ScacchieraGamestate gameState;

    @Enumerated(EnumType.STRING)
    private Color color;

    @JsonIgnore
    private Piece pezzo;

    private String nomePezzo;



    public Casella(int row, int column)
    {
        this.row = row;
        this.column = column;
        this.nomeCasella= ChessUtils.positionToString(row, column);
    }

    public void setPezzo(Piece pezzo) {
        if (this.pezzo==null)
            this.pezzo = pezzo;
        pezzo.setPosizione(this);
    }

    public void svuotaCasella() {
        this.pezzo = null;
    }

    public void setSalvataggio(Piece pezzo ) {
        this.nomePezzo = pezzo.getNome();
    }

}
