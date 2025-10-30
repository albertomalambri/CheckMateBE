package com.generation.checkmatebe.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.utilities.ChessUtils;
import lombok.Getter;
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

//    @JsonIgnore
    private Pezzo pezzo;
    private Color colorePezzo;

    private boolean giaMosso = false;


    @JsonCreator
    public Casella(@JsonProperty("row") int row,@JsonProperty("column") int column)
    {
        this.row = row;
        this.column = column;
        this.nomeCasella= ChessUtils.positionToString(row, column);
    }

    public void svuotaCasella()
    {
        this.pezzo = null;
        this.setColorePezzo(null);
    }

}
