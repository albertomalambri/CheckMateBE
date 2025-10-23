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

public class Queen extends Piece
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
        boolean stop1 = true;
        boolean stop2 = true;
        boolean stop3 = true;
        boolean stop4 = true;
        boolean stop5 = true;
        boolean stop6 = true;
        boolean stop7 = true;
        boolean stop8 = true;
        for (int i = 1; i < 8; i++) {
            if (stop1) {
                if (row+i<8) {
                    if (scacchiera[row + i][column].getPezzo() == null)
                        pos.add(new Casella(row + i, column));
                    else if (canEat(scacchiera[row + i][column].getPezzo())) {
                        pos.add(new Casella(row + i, column));
                        stop1 = false;
                    } else
                        stop1 = false;
                }
            }
            if (stop2) {
                if (row-i>=0) {
                    if (scacchiera[row - i][column].getPezzo() == null)
                        pos.add(new Casella(row - i, column));
                    else if (canEat(scacchiera[row - i][column].getPezzo())) {
                        pos.add(new Casella(row - i, column));
                        stop2 = false;
                    } else
                        stop2 = false;
                }
            }
            if (stop3) {
                if (column+i<8) {
                    if (scacchiera[row][column + i].getPezzo() == null)
                        pos.add(new Casella(row, column + i));
                    else if (canEat(scacchiera[row][column + i].getPezzo())) {
                        pos.add(new Casella(row, column + i));
                        stop3 = false;
                    } else
                        stop3 = false;
                }
            }
            if (stop4) {
                if (column-i>=0) {
                    if (scacchiera[row][column - i].getPezzo() == null)
                        pos.add(new Casella(row, column - i));
                    else if (canEat(scacchiera[row][column - i].getPezzo())) {
                        pos.add(new Casella(row, column - i));
                        stop4 = false;
                    } else
                        stop4 = false;
                }
            }
            if (stop5) {
                if (row+i<8 && column+i<8 ) {
                    if (scacchiera[row + i][column + i].getPezzo() == null)
                        pos.add(new Casella(row + i, column + i));
                    else if (canEat(scacchiera[row + i][column + i].getPezzo())) {
                        pos.add(new Casella(row + i, column + i));
                        stop5 = false;
                    } else
                        stop5 = false;
                }
            }
            if (stop6) {
                if (row+i<8 && column-i>=0) {
                    if (scacchiera[row + i][column - i].getPezzo() == null)
                        pos.add(new Casella(row + i, column - i));
                    else if (canEat(scacchiera[row + i][column - i].getPezzo())) {
                        pos.add(new Casella(row + i, column - i));
                        stop6 = false;
                    } else
                        stop6 = false;
                }
            }
            if (stop7) {
                if (row-i>=0 && column+i<8) {
                    if (scacchiera[row - i][column + i].getPezzo() == null)
                        pos.add(new Casella(row - i, column + i));
                    else if (canEat(scacchiera[row - i][column + i].getPezzo())) {
                        pos.add(new Casella(row - i, column + i));
                        stop7 = false;
                    } else
                        stop7 = false;
                }
            }
            if (stop8) {
                if (row-i>=0 && column-i>=0) {
                    if (scacchiera[row - i][column - i].getPezzo() == null)
                        pos.add(new Casella(row - i, column - i));
                    else if (canEat(scacchiera[row - i][column - i].getPezzo())) {
                        pos.add(new Casella(row - i, column - i));
                        stop8 = false;
                    } else
                        stop8 = false;
                }
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
        if (this.isGiaMosso() && this.getColor()== Color.BIANCO)
            return "queen_b_true";
        if (this.isGiaMosso() && this.getColor() == Color.NERO)
            return "queen_n_true";
        if (!this.isGiaMosso() && this.getColor()== Color.BIANCO)
            return "queen_b_false";
        if (!this.isGiaMosso() && this.getColor()== Color.NERO)
            return "queen_n_false";
        return "";
    }
}
