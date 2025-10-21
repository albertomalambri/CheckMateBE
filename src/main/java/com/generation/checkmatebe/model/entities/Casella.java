package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.entities.pieces.Piece;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Casella
{
//    private final int row;     // da 1 a 8
//    private final int column;
    // da 1 a 8 (oppure 'a'–'h' se vuoi usare lettere)
    private String nomeCasella; //es e4

    @Enumerated(EnumType.STRING)
    private Color color;

    private Piece pezzo;

    public Casella() {}

    public Casella(int row, int column)
    {
        this.nomeCasella= ChessUtils.positionToString(row, column);
    }

    public void setPezzo(Piece pezzo) {
        if (this.pezzo==null)
            this.pezzo = pezzo;
        pezzo.setPosizione(this);
    }

    //Delta sta per variazione o spostamento

//    public Position avanti(int delta)
//    {
//        return new Position(row + delta, column);
//    }
//
//    public Position diagonale(int deltaRow, int deltaColumn)
//    {
//        return new Position(row + deltaRow, column + deltaColumn);
//    }

    // equals, hashCode, toString...
}
