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
public class Pawn extends Piece
{

    @Override
    public void setPosizione(Casella posizione) {
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
        if (this.getColor().equals(Color.BIANCO)) {
            if (row!=1 && row+1<8 && scacchiera[row+1][column].getPezzo()==null)
                pos.add(new Casella(row + 1, column));
            else {
                if (scacchiera[row+1][column].getPezzo()==null)
                    pos.add(new Casella(row + 1, column));
                if (scacchiera[row+2][column].getPezzo()==null)
                    pos.add(new Casella(row + 2, column));
            }

            if(row+1<8 && column+1<8 && scacchiera[row+1][column+1].getPezzo()!=null && canEat(scacchiera[row+1][column+1].getPezzo()))
                pos.add(new Casella(row+1,column+1));
            if (row+1<8 && column-1>=0 && scacchiera[row+1][column-1].getPezzo()!=null && canEat(scacchiera[row+1][column-1].getPezzo()))
                pos.add(new Casella(row+1,column-1));
        }
        else if (this.getColor().equals(Color.NERO)) {
            if (row!=6 && row-1>=0 && scacchiera[row-1][column].getPezzo()==null)
                pos.add(new Casella(row - 1, column));

            else {
                if (scacchiera[row-1][column].getPezzo()==null)
                    pos.add(new Casella(row - 1, column));
                if (scacchiera[row-2][column].getPezzo()==null)
                    pos.add(new Casella(row - 2, column));
            }

            if(row-1>=0 && column+1<8 && scacchiera[row-1][column+1].getPezzo()!=null && canEat(scacchiera[row-1][column+1].getPezzo()))
                pos.add(new Casella(row+1,column+1));
            if (row-1>=0 && column-1>=0 && scacchiera[row-1][column-1].getPezzo()!=null && canEat(scacchiera[row-1][column-1].getPezzo()))
                pos.add(new Casella(row+1,column-1));
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
        if (this.isGiaMosso() && this.getColor()== Color.BIANCO)
            return "pawn_b_true";
        if (this.isGiaMosso() && this.getColor() == Color.NERO)
            return "pawn_n_true";
        if (!this.isGiaMosso() && this.getColor()== Color.BIANCO)
            return "pawn_b_false";
        if (!this.isGiaMosso() && this.getColor()== Color.NERO)
            return "pawn_n_false";
        return "";
    }
}

