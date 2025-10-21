package com.generation.checkmatebe.utilities;

import com.generation.checkmatebe.dtos.PieceDTO;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.pieces.Piece;

import java.util.List;

public final class ChessUtils {

    // Costruttore privato per evitare istanziazione
    private ChessUtils() {
        throw new UnsupportedOperationException("Classe di utilità - non istanziabile");
    }

    // Esempio di metodo statico
    public static boolean isValidPosition(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }

    public static String positionToString(int row, int column) {
        char colLetter = (char) ('a' + column);
        return colLetter + String.valueOf(row + 1);
    }

    public static int getColumnIndex(char columnLetter) {
        return columnLetter - 'a';
    }

    public static PieceDTO converti(Piece piece)
    {
        String tipo = piece.getClass().getSimpleName().toLowerCase(); // "bishop", "pawn", ecc.
        String colore = piece.getColor().name();                      // enum → stringa
        String posizione = piece.getPosizione().getNomeCasella();   // es. (1,4) → "e2"
        return new PieceDTO(tipo, colore, posizione);
    }

//    private static String convertiPosizione(Posizione pos)
//    {
//        char col = (char) ('a' + pos.getColumn() - 1);
//        int row = pos.getRow();
//        return "" + col + row;
//    }
}