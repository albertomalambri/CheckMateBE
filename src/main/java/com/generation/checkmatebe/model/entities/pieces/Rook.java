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

public class Rook extends Piece
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
        boolean stop1 = true;
        boolean stop2 = true;
        boolean stop3 = true;
        boolean stop4 = true;
        for (int i = 1; i < 8; i++) {
            if (stop1) {
                if (scacchiera[row+i][column].getPezzo()==null)
                    pos.add(new Casella(row + i, column));
                else if (canEat(scacchiera[row+i][column].getPezzo())) {
                    pos.add(new Casella(row + i, column));
                    stop1=false;
                }
                else
                    stop1=false;
            }
            if (stop2) {
                if (scacchiera[row-i][column].getPezzo()==null)
                    pos.add(new Casella(row-i, column));
                else if (canEat(scacchiera[row-i][column].getPezzo())) {
                    pos.add(new Casella(row-i, column));
                    stop2=false;
                }
                else
                    stop2=false;
            }
            if (stop3) {
                if (scacchiera[row][column+i].getPezzo()==null)
                    pos.add(new Casella(row, column+i));
                else if (canEat(scacchiera[row][column+i].getPezzo())) {
                    pos.add(new Casella(row, column+i));
                    stop3=false;
                }
                else
                    stop3=false;
            }
            if (stop4) {
                if (scacchiera[row][column-i].getPezzo()==null)
                    pos.add(new Casella(row, column-i));
                else if (canEat(scacchiera[row][column-i].getPezzo())) {
                    pos.add(new Casella(row, column-i));
                    stop4=false;
                }
                else
                    stop4=false;
            }
        }
        if(!this.isGiaMosso()) {
            if (this.getColor()== Color.BIANCO && scacchiera[0][6].getPezzo()==null && scacchiera[0][5].getPezzo()==null && scacchiera[0][4].getPezzo()!=null && !scacchiera[0][4].getPezzo().isGiaMosso())
                pos.add(new Casella(0, 5));
            if (this.getColor()== Color.BIANCO && scacchiera[0][1].getPezzo()==null && scacchiera[0][2].getPezzo()==null && scacchiera[0][3].getPezzo()==null && scacchiera[0][4].getPezzo()!=null && !scacchiera[0][4].getPezzo().isGiaMosso())
                pos.add(new Casella(0, 3));
            if (this.getColor()== Color.NERO && scacchiera[7][6].getPezzo()==null && scacchiera[7][5].getPezzo()==null && scacchiera[7][4].getPezzo()!=null && !scacchiera[7][4].getPezzo().isGiaMosso())
                pos.add(new Casella(7, 5));
            if (this.getColor()== Color.NERO && scacchiera[7][1].getPezzo()==null && scacchiera[7][2].getPezzo()==null && scacchiera[7][3].getPezzo()==null && scacchiera[7][4].getPezzo()!=null && !scacchiera[7][4].getPezzo().isGiaMosso())
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

    @Override
    public String getNome() {
        return "rook";
    }
}
