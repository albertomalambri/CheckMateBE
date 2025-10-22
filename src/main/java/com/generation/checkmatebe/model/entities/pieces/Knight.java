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
    public List<Casella> calcolaMossePossibili() {

        Casella[][] scacchiera = this.getPosizione().getGameState().getScacchiera();

        int row = Integer.parseInt(""+this.getPosizione().getNomeCasella().charAt(1));
        int column = ChessUtils.getColumnIndex(this.getPosizione().getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        if (scacchiera[row+2][column+1].getPezzo()==null || canEat(scacchiera[row+2][column+1].getPezzo()))
            pos.add(new Casella(row+2, column+1));
        if (scacchiera[row+2][column-1].getPezzo()==null || canEat(scacchiera[row+2][column-1].getPezzo()))
            pos.add(new Casella(row+2, column-1));
        if (scacchiera[row-2][column+1].getPezzo()==null || canEat(scacchiera[row-2][column+1].getPezzo()))
            pos.add(new Casella(row-2, column+1));
        if (scacchiera[row-2][column-1].getPezzo()==null || canEat(scacchiera[row-2][column-1].getPezzo()))
            pos.add(new Casella(row-2, column-1));
        if (scacchiera[row+1][column+2].getPezzo()==null || canEat(scacchiera[row+1][column+2].getPezzo()))
            pos.add(new Casella(row+1, column+2));
        if (scacchiera[row-1][column+2].getPezzo()==null || canEat(scacchiera[row-1][column+2].getPezzo()))
            pos.add(new Casella(row-1, column+2));
        if (scacchiera[row+1][column-2].getPezzo()==null || canEat(scacchiera[row+1][column-2].getPezzo()))
            pos.add(new Casella(row+1, column-2));
        if (scacchiera[row-1][column-2].getPezzo()==null || canEat(scacchiera[row-1][column-2].getPezzo()))
            pos.add(new Casella(row-1, column-2));

        pos.removeIf(positions -> column < 0 || column > 7 || row < 0 || row > 7);
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }

    @Override
    public String getNome() {
        return "knight";
    }
}
