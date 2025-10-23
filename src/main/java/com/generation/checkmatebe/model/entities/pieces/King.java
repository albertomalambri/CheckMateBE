package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class King extends Piece
{
    @Override
    public void setPosizione(Casella posizione)
    {
        if (posizione.getPezzo()==null)
            posizione.setPezzo(this);
        super.setPosizione(posizione);
    }


    @Override
    public List<Casella> calcolaMossePossibili()
    {
        Casella[][] scacchiera = this.getPosizione().getGameState().getScacchiera();
        int row = Integer.parseInt(""+this.getPosizione().getNomeCasella().charAt(1))-1;
        int column = ChessUtils.getColumnIndex(this.getPosizione().getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        if (row+1<8 && (scacchiera[row+1][column].getPezzo()==null || canEat(scacchiera[row+1][column].getPezzo())))
            pos.add(new Casella(row+1, column));
        if (row-1>=0 && (scacchiera[row-1][column].getPezzo()==null || canEat(scacchiera[row-1][column].getPezzo())))
            pos.add(new Casella(row-1, column));
        if (column+1<8 && (scacchiera[row][column+1].getPezzo()==null || canEat(scacchiera[row][column+1].getPezzo())))
            pos.add(new Casella(row, column+1));
        if (column-1>=0 && (scacchiera[row][column-1].getPezzo()==null || canEat(scacchiera[row][column-1].getPezzo())))
            pos.add(new Casella(row, column-1));
        if (row+1<8 && column-1>=0 && (scacchiera[row+1][column-1].getPezzo()==null || canEat(scacchiera[row+1][column-1].getPezzo())))
            pos.add(new Casella(row+1, column-1));
        if (row+1<8 && column+1<8 && (scacchiera[row+1][column+1].getPezzo()==null || canEat(scacchiera[row+1][column+1].getPezzo())))
            pos.add(new Casella(row+1, column+1));
        if (row-1>=0 && column+1<8 && (scacchiera[row-1][column+1].getPezzo()==null || canEat(scacchiera[row-1][column+1].getPezzo())))
            pos.add(new Casella(row-1, column+1));
        if (row-1>=0 && column-1>=0 && (scacchiera[row-1][column-1].getPezzo()==null || canEat(scacchiera[row-1][column-1].getPezzo())))
            pos.add(new Casella(row-1, column-1));

        if(!this.isGiaMosso()) {
            if (this.getColor()== Color.BIANCO && scacchiera[0][6].getPezzo()==null && scacchiera[0][5].getPezzo()==null && !scacchiera[0][7].getPezzo().isGiaMosso())
                pos.add(new Casella(0, 6));
            if (this.getColor()== Color.BIANCO && scacchiera[0][1].getPezzo()==null && scacchiera[0][2].getPezzo()==null && scacchiera[0][3].getPezzo()==null && !scacchiera[0][0].getPezzo().isGiaMosso())
                pos.add(new Casella(0, 2));
            if (this.getColor()== Color.NERO && scacchiera[7][6].getPezzo()==null && scacchiera[7][5].getPezzo()==null && !scacchiera[7][7].getPezzo().isGiaMosso())
                pos.add(new Casella(7, 6));
            if (this.getColor()== Color.NERO && scacchiera[7][1].getPezzo()==null && scacchiera[7][2].getPezzo()==null && scacchiera[7][3].getPezzo()==null && !scacchiera[7][0].getPezzo().isGiaMosso())
                pos.add(new Casella(7, 2));

        }
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }

    @Override
    public String getNome() {
        return "king";
    }
}
