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
public class Bishop extends Piece
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
        boolean dir1 = true; //in basso a destra
        boolean dir2 = true; //in basso a sinistra
        boolean dir3 = true; //in alto a destra
        boolean dir4 = true;//in alto a sinistra
/**
     0 1 2 3 4 5 6 7

 0   r n b . k b n r
 1   p p p p p p p p
 2   . . . . . . q .
 3   . . . . . . . .
 4   . . . . . . . .
 5   . . . K . . . .
 6   . . . . . . . .
 7   . . . . . . . .


 */
        for (int i=1; i<8; i++) {
            if (dir1) {
                if (row+i<8 && column+i<8 ) {
                    if (scacchiera[row + i][column + i].getPezzo() == null)
                        pos.add(new Casella(row + i, column + i));
                    else if (canEat(scacchiera[row + i][column + i].getPezzo())) {
                        pos.add(new Casella(row + i, column + i));
                        dir1 = false;
                    } else
                        dir1 = false;
                }
            }
            if (dir2) {
                if (row+i<8 && column-i>=0) {
                    if (scacchiera[row + i][column - i].getPezzo() == null)
                        pos.add(new Casella(row + i, column - i));
                    else if (canEat(scacchiera[row + i][column - i].getPezzo())) {
                        pos.add(new Casella(row + i, column - i));
                        dir2 = false;
                    } else
                        dir2 = false;
                }
            }
            if (dir3) {
                if (row-i>=0 && column+i<8) {
                    if (scacchiera[row - i][column + i].getPezzo() == null)
                    pos.add(new Casella(row - i, column + i));
                else if (canEat(scacchiera[row - i][column + i].getPezzo())) {
                        pos.add(new Casella(row - i, column + i));
                        dir3 = false;
                    } else
                        dir3 = false;
                }
            }
            if (dir4) {
                if (row-i>=0 && column-i>=0) {
                    if (scacchiera[row - i][column - i].getPezzo() == null)
                        pos.add(new Casella(row - i, column - i));
                    else if (canEat(scacchiera[row - i][column - i].getPezzo())) {
                        pos.add(new Casella(row - i, column - i));
                        dir4 = false;
                    } else
                        dir4 = false;
                }
            }
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
            return "bishop_b_true";
        if (this.isGiaMosso() && this.getColor() == Color.NERO)
            return "bishop_n_true";
        if (!this.isGiaMosso() && this.getColor()== Color.BIANCO)
            return "bishop_b_false";
        if (!this.isGiaMosso() && this.getColor()== Color.NERO)
            return "bishop_n_false";
        return "";
    }
}
