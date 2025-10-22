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
        int row = Integer.parseInt(""+this.getPosizione().getNomeCasella().charAt(1));
        int column = ChessUtils.getColumnIndex(this.getPosizione().getNomeCasella().charAt(0));
        List<Casella> pos = new ArrayList<>();
        boolean stop1 = true;
        boolean stop2 = true;
        boolean stop3 = true;
        boolean stop4 = true;

        for (int i=1; i<8; i++) {
            if (stop1) {
                if (scacchiera[row + i][column + i].getPezzo() == null)
                    pos.add(new Casella(row + i, column + i));
                else if (canEat(scacchiera[row + i][column + i].getPezzo())) {
                    pos.add(new Casella(row + i, column + i));
                    stop1=false;
                }
                else
                    stop1=false;
            }
            if (stop2) {
                if (scacchiera[row + i][column - i].getPezzo() == null)
                    pos.add(new Casella(row + i, column - i));
                else if (canEat(scacchiera[row + i][column - i].getPezzo())) {
                    pos.add(new Casella(row + i, column - i));
                    stop2=false;
                }
                else
                    stop2=false;
            }
            if (stop3) {
                if (scacchiera[row - i][column + i].getPezzo() == null)
                    pos.add(new Casella(row - i, column + i));
                else if (canEat(scacchiera[row - i][column + i].getPezzo())) {
                    pos.add(new Casella(row - i, column + i));
                    stop3=false;
                }
                else
                    stop3=false;
            }
            if (stop4) {
                if (scacchiera[row - i][column - i].getPezzo() == null)
                    pos.add(new Casella(row - i, column - i));
                else if (canEat(scacchiera[row - i][column - i].getPezzo())) {
                    pos.add(new Casella(row - i, column - i));
                    stop4=false;
                }
                else
                    stop4=false;
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

    @Override
    public String getNome() {
        return "bishop";
    }
}
