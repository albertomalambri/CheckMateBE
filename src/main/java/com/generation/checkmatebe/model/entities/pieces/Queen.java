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
        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        boolean dir5 = true;
        boolean dir6 = true;
        boolean dir7 = true;
        boolean dir8 = true;
        for (int i = 1; i < 8; i++) {
            if (dir1) {
                if (row+i<8) {
                    if (scacchiera[row + i][column].getPezzo() == null)
                        pos.add(new Casella(row + i, column));
                    else if (canEat(scacchiera[row + i][column].getPezzo())) {
                        pos.add(new Casella(row + i, column));
                        dir1 = false;
                    } else
                        dir1 = false;
                }
            }
            if (dir2) {
                if (row-i>=0) {
                    if (scacchiera[row - i][column].getPezzo() == null)
                        pos.add(new Casella(row - i, column));
                    else if (canEat(scacchiera[row - i][column].getPezzo())) {
                        pos.add(new Casella(row - i, column));
                        dir2 = false;
                    } else
                        dir2 = false;
                }
            }
            if (dir3) {
                if (column+i<8) {
                    if (scacchiera[row][column + i].getPezzo() == null)
                        pos.add(new Casella(row, column + i));
                    else if (canEat(scacchiera[row][column + i].getPezzo())) {
                        pos.add(new Casella(row, column + i));
                        dir3 = false;
                    } else
                        dir3 = false;
                }
            }
            if (dir4) {
                if (column-i>=0) {
                    if (scacchiera[row][column - i].getPezzo() == null)
                        pos.add(new Casella(row, column - i));
                    else if (canEat(scacchiera[row][column - i].getPezzo())) {
                        pos.add(new Casella(row, column - i));
                        dir4 = false;
                    } else
                        dir4 = false;
                }
            }
            if (dir5) {
                if (row+i<8 && column+i<8 ) {
                    if (scacchiera[row + i][column + i].getPezzo() == null)
                        pos.add(new Casella(row + i, column + i));
                    else if (canEat(scacchiera[row + i][column + i].getPezzo())) {
                        pos.add(new Casella(row + i, column + i));
                        dir5 = false;
                    } else
                        dir5 = false;
                }
            }
            if (dir6) {
                if (row+i<8 && column-i>=0) {
                    if (scacchiera[row + i][column - i].getPezzo() == null)
                        pos.add(new Casella(row + i, column - i));
                    else if (canEat(scacchiera[row + i][column - i].getPezzo())) {
                        pos.add(new Casella(row + i, column - i));
                        dir6 = false;
                    } else
                        dir6 = false;
                }
            }
            if (dir7) {
                if (row-i>=0 && column+i<8) {
                    if (scacchiera[row - i][column + i].getPezzo() == null)
                        pos.add(new Casella(row - i, column + i));
                    else if (canEat(scacchiera[row - i][column + i].getPezzo())) {
                        pos.add(new Casella(row - i, column + i));
                        dir7 = false;
                    } else
                        dir7 = false;
                }
            }
            if (dir8) {
                if (row-i>=0 && column-i>=0) {
                    if (scacchiera[row - i][column - i].getPezzo() == null)
                        pos.add(new Casella(row - i, column - i));
                    else if (canEat(scacchiera[row - i][column - i].getPezzo())) {
                        pos.add(new Casella(row - i, column - i));
                        dir8 = false;
                    } else
                        dir8 = false;
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
