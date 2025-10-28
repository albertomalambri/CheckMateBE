package com.generation.checkmatebe.utilities;

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
        int invertedRow = 8 - row; // Inverte la riga: 0 → 8, 1 → 7, ..., 7 → 1
        return "" + colLetter + invertedRow;
    }


    public static int getColumnIndex(char columnLetter) {
        return columnLetter - 'a';
    } //101-97 4

//    public static PieceDTO converti(Piece piece)
//    {
//        String tipo = piece.getClass().getSimpleName().toLowerCase(); // "bishop", "pawn", ecc.
//        String colore = piece.getColor().name();                      // enum → stringa
//        String posizione = piece.getPosizione().getNomeCasella();   // es. (1,4) → "e2"
//        return new PieceDTO(tipo, colore, posizione);
//    }

    public static int getRowIndex(char rowLetter) {
        return 8 - Character.getNumericValue(rowLetter); // 8 - 4 = 4
    }

}