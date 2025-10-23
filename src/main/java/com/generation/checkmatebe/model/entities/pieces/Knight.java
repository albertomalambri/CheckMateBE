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

        int row = Integer.parseInt(""+this.getPosizione().getNomeCasella().charAt(1))-1;
        int column = ChessUtils.getColumnIndex(this.getPosizione().getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        if (row+2<8 && column+1<8 && (scacchiera[row+2][column+1].getPezzo()==null || canEat(scacchiera[row+2][column+1].getPezzo())))
            pos.add(new Casella(row+2, column+1));
        if (column-1>=0 && row+2<8 && (scacchiera[row+2][column-1].getPezzo()==null || canEat(scacchiera[row+2][column-1].getPezzo())))
            pos.add(new Casella(row+2, column-1));
        if (row-2>=0 && column+1<8 && (scacchiera[row-2][column+1].getPezzo()==null || canEat(scacchiera[row-2][column+1].getPezzo())))
            pos.add(new Casella(row-2, column+1));
        if (row-2>=0 && column-1>=0 && (scacchiera[row-2][column-1].getPezzo()==null || canEat(scacchiera[row-2][column-1].getPezzo())))
            pos.add(new Casella(row-2, column-1));
        if (row+1<8 && column+2<8 && (scacchiera[row+1][column+2].getPezzo()==null || canEat(scacchiera[row+1][column+2].getPezzo())))
            pos.add(new Casella(row+1, column+2));
        if (row-1>=0 && column+2<8 && (scacchiera[row-1][column+2].getPezzo()==null || canEat(scacchiera[row-1][column+2].getPezzo())))
            pos.add(new Casella(row-1, column+2));
        if (row+1<8 && column-2>=0 && (scacchiera[row+1][column-2].getPezzo()==null || canEat(scacchiera[row+1][column-2].getPezzo())))
            pos.add(new Casella(row+1, column-2));
        if (row-1>=0 && column-2>=0 && (scacchiera[row-1][column-2].getPezzo()==null || canEat(scacchiera[row-1][column-2].getPezzo())))
            pos.add(new Casella(row-1, column-2));
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
