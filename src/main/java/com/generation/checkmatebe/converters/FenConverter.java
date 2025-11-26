package com.generation.checkmatebe.converters;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;

public class FenConverter {
    public static String boardToFen(Casella[][] board,
                                    Color turno
                                    ) {
        StringBuilder fen = new StringBuilder();

        // Riga 8 (board[0]) fino a riga 1 (board[7])
        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;
            for (int col = 0; col < 8; col++) {
                Casella cella = board[row][col];
                if (cella.getPezzo() == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(toFenChar(cella.getPezzo(), cella.getColorePezzo()));
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            if (row < 7) fen.append('/');
        }

        // Turno
        fen.append(" ").append(turno == Color.BIANCO ? "w" : "b");

        // Arrocco
        //fen.append(" ").append(arrocco.isEmpty() ? "-" : arrocco);

        // En passant
        //fen.append(" ").append(enPassant == null ? "-" : enPassant);

        // Halfmove e fullmove
        //fen.append(" ").append(halfmoveClock).append(" ").append(fullmoveNumber);

        return fen.toString();
    }

    private static char toFenChar(Pezzo pezzo, Color colore) {
        char c;
        switch (pezzo) {
            case PEDONE: c = 'p'; break;
            case CAVALLO: c = 'n'; break;
            case ALFIERE: c = 'b'; break;
            case TORRE: c = 'r'; break;
            case REGINA: c = 'q'; break;
            case RE: c = 'k'; break;
            default: throw new IllegalArgumentException("Pezzo non valido: " + pezzo);
        }
    return colore == Color.BIANCO ? Character.toUpperCase(c) : c;
    }
}
