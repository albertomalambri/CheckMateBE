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

public class Knight extends Piece
{

    @Override
    public void setPosizione(Casella posizione) {
        if (posizione.getPezzo()==null)
            posizione.setPezzo(this);
        super.setPosizione(posizione);
    }

    @Override
    public List<Casella> calcolaMossePossibili(Casella casella) {

        int row = Integer.parseInt(""+casella.getNomeCasella().charAt(1));
        int column = ChessUtils.getColumnIndex(casella.getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        pos.add(new Casella(row+2, column+1));
        pos.add(new Casella(row+2, column-1));
        pos.add(new Casella(row-2, column+1));
        pos.add(new Casella(row-2, column-1));

        pos.add(new Casella(row+1, column+2));
        pos.add(new Casella(row-1, column+2));
        pos.add(new Casella(row+1, column-2));
        pos.add(new Casella(row-1, column-2));

        pos.removeIf(positions -> column < 0 || column > 7 || row < 0 || row > 7);
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }
}
