package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pawn extends Piece
{

    @Override
    public void setPosizione(Casella posizione) {
        if (posizione.getPezzo()==null)
            posizione.setPezzo(this);
        super.setPosizione(posizione);
    }

    @Override
    public List<Casella> calcolaMossePossibili(Casella casella)
    {

        int row = Integer.parseInt(""+casella.getNomeCasella().charAt(1));
        int column = ChessUtils.getColumnIndex(casella.getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        if (this.getColor().equals(Color.BIANCO)) {
            if (row!=1)
                pos.add(new Casella(row + 1, column));
            else {
                pos.add(new Casella(row + 1, column));
                pos.add(new Casella(row + 2, column));
            }
        }
        else if (this.getColor().equals(Color.NERO)) {
            if (row!=6)
                pos.add(new Casella(row - 1, column));

            else {
                pos.add(new Casella(row - 1, column));
                pos.add(new Casella(row - 2, column));
            }
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

