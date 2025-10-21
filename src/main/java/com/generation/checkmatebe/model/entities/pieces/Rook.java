package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Rook extends Piece
{

    @Override
    public void setPosizione(Casella posizione) {
        if (posizione.getPezzo()==null)
            posizione.setPezzo(this);
        super.setPosizione(posizione);
    }

    private boolean giaMosso = false;


    @Override
    public List<Casella> calcolaMossePossibili(Casella casella) {

        int row = Integer.parseInt(""+casella.getNomeCasella().charAt(1));
        int column = ChessUtils.getColumnIndex(casella.getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            pos.add(new Casella(row+i, column));
            pos.add(new Casella(row-i, column));
            pos.add(new Casella(row, column+i));
            pos.add(new Casella(row, column-i));
        }
        if(!this.giaMosso) {
            if (row == 0 && column == 7)
                pos.add(new Casella(0, 5));
            if (row == 0 && column == 0)
                pos.add(new Casella(0, 3));
            if (row == 7 && column == 7)
                pos.add(new Casella(7, 5));
            if (row == 7 && column == 0)
                pos.add(new Casella(7, 3));
        }
        pos.removeIf(positions -> column < 0 || column > 7 || row < 0 || row > 7);
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }
}
