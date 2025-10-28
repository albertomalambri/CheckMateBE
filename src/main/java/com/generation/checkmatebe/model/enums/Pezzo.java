package com.generation.checkmatebe.model.enums;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.utilities.ChessUtils;

public enum Pezzo {
    PEDONE(1, "PE") {
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Mossa m) {
            //solo controlli specifici per pedone
            Casella[][] scacchiera = gameState.getScacchiera();
            int colStart = m.getStart().getColumn();
            int rowStart = m.getStart().getRow();
            int colEnd = m.getEnd().getColumn();
            int rowEnd = m.getEnd().getRow();
            //controllo se bianco è stato mosso
            if ((rowStart == 6 && scacchiera[rowStart - 1][colStart] == null) && m.getStart().getColorePezzo() == Color.BIANCO)
                return ((m.getEnd().getPezzo() == null) && (colEnd == colStart) && (rowEnd == rowStart - 2 || rowEnd == rowStart - 1));

            else if ((rowStart == 1 && scacchiera[rowStart + 1][colStart] == null) && m.getStart().getColorePezzo() == Color.NERO)
                return ((m.getEnd().getPezzo() == null) && (colEnd == colStart) && (rowEnd == rowStart + 2 || rowEnd == rowStart + 1));


            //METODO CanEAT
            //caso pedone bianco
            else if (m.getStart().getColorePezzo() == Color.BIANCO) {
                if (colEnd == colStart - 1 && rowEnd == rowStart - 1 || colEnd == colStart + 1 && rowEnd == rowStart - 1) {
                    return (m.getEnd().getColorePezzo() != m.getStart().getColorePezzo());
                }
                //avanzamento base pedone
                return colEnd == colStart && rowEnd == rowStart - 1;

            }
            //caso pedone nero
            else if (m.getStart().getColorePezzo() == Color.NERO) {
                if (colEnd == colStart - 1 && rowEnd == rowStart + 1 || colEnd == colStart + 1 && rowEnd == rowStart + 1) {
                    return m.getEnd().getColorePezzo() != m.getStart().getColorePezzo();
                }
                //avanzamento base pedone
                return colEnd == colStart && rowEnd == rowStart + 1;
            }
            return false;
        }

    },

    //possibile semplificare con delta(Cavallo)

    CAVALLO(3, "CA") {
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Mossa m) {
            //solo controlli specifici per cavallo
            int colStart = m.getStart().getColumn();
            int rowStart = m.getStart().getRow();
            int colEnd = m.getEnd().getColumn();
            int rowEnd = m.getEnd().getRow();

            if (m.getEnd().getPezzo() == null || m.getEnd().getColorePezzo() != m.getStart().getColorePezzo()) {
                // 2 in avanti + 1 a destra o sinistra
                if ((rowEnd == rowStart - 2) && (colEnd == colStart + 1 || colEnd == colStart - 1))
                    return true;
                //1 in avanti + 2 a destra o sinistra
                if ((rowEnd == rowStart - 1) && (colEnd == colStart + 2 || colEnd == colStart - 2))
                    return true;

                // 2 indietro + 1 a destra o sinistra
                if ((rowEnd == rowStart + 2) && (colEnd == colStart + 1 || colEnd == colStart - 1))
                    return true;

                //1 indietro + 2 a destra o sinistra
                if ((rowEnd == rowStart + 1) && (colEnd == colStart + 2 || colEnd == colStart - 2))
                    return true;

                // 2 a destra + 1 in avanti o dietro
                if ((colEnd == colStart + 2) && (rowEnd == rowStart + 1 || rowEnd == rowStart - 1))
                    return true;

                //1 a destra + 2 in avanti o dietro
                if ((colEnd == colStart + 1) && (rowEnd == rowStart + 2 || rowEnd == rowStart - 2))
                    return true;

                // 2 a sinistra + 1 in avanti o dietro
                if ((colEnd == colStart - 2) && (rowEnd == rowStart + 1 || rowEnd == rowStart - 1))
                    return true;

                //1 a sinistra + 2 in avanti o dietro
                if ((colEnd == colStart - 1) && (rowEnd == rowStart + 2 || rowEnd == rowStart - 2))
                    return true;
            }
            return false;
        }
    },

        ALFIERE(3,"AL") {
            @Override
            boolean mossaValidaSpecifica (ScacchieraGamestate gameState, Mossa m)
            {
                int colStart = m.getStart().getColumn();
                int rowStart = m.getStart().getRow();
                int colEnd = m.getEnd().getColumn();
                int rowEnd = m.getEnd().getRow();
                //solo controlli specifici per alfiere
                return Math.abs(rowEnd - rowStart) == Math.abs(colEnd - colStart) &&
                        isDiagonalPathClear(gameState, rowStart, colStart, rowEnd, colEnd);
            }
        },

        TORRE(5,"TO") {
            @Override
            boolean mossaValidaSpecifica (ScacchieraGamestate gameState, Mossa m)
            {
                int colStart = m.getStart().getColumn();
                int rowStart = m.getStart().getRow();
                int colEnd = m.getEnd().getColumn();
                int rowEnd = m.getEnd().getRow();

                return (rowStart==rowEnd || colStart==colEnd) &&
                        isStraightPathClear(gameState, rowStart, colStart, rowEnd, colEnd) &&
                        m.getEnd().getColorePezzo() != m.getStart().getColorePezzo();

            }

        },

        REGINA(9,"RG") {
            @Override
            boolean mossaValidaSpecifica (ScacchieraGamestate gameState, Mossa m){
                //solo controlli specifici per regina
                int colStart = m.getStart().getColumn();
                int rowStart = m.getStart().getRow();
                int colEnd = m.getEnd().getColumn();
                int rowEnd = m.getEnd().getRow();

                
                return isStraightPathClear(gameState, rowStart, colStart, rowEnd, colEnd) &&
                        isDiagonalPathClear(gameState, rowStart, colStart, rowEnd, colEnd) &&
                        m.getEnd().getColorePezzo() != m.getStart().getColorePezzo();
            }
        },

        RE(0,"RE") {
            @Override
            boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Mossa m) {
                int colStart = m.getStart().getColumn();
                int rowStart = m.getStart().getRow();
                int colEnd = m.getEnd().getColumn();
                int rowEnd = m.getEnd().getRow();
                Casella[][] scacchiera = gameState.getScacchiera();

                int[] dirCheck = {rowEnd - rowStart, colEnd - colStart};  // {1,-1}
                //solo controlli specifici per re
                int[][] direzioni = {
                        {-1, -1}, //alto sinistra
                        {-1, 0}, //alto
                        {-1, 1}, //alto destra
                        {0, 1}, //destra
                        {1, 1}, //basso destra
                        {1, 0}, //basso
                        {1, -1}, //basso sinistra
                        {0, -1} //sinistra
                };

                //controllo per vedere se dove va il re è occupato da un pezzo alleato


                for (int[] dir : direzioni)
                {
                    if (Arrays.equals(dirCheck, dir)) {
                        if (m.getEnd().getPezzo() != null &&
                                m.getEnd().getColorePezzo() == m.getStart().getColorePezzo())
                        {
                            return false; //❌ non può muoversi su pezzo alleato
                        }
                        return true; //✅ direzione valida e casella libera o con nemico
                    }
                }
                return false; //❌ direzione non valida
            }
        };


        public static Pezzo getByCodice(String codice) {
            for (Pezzo p : Pezzo.values())
                if (p.getCodice().equalsIgnoreCase(codice))
                    return p;
            return null;
        }

        Pezzo(int valore, String codice) {
            this.valore = valore;
            this.codice = codice;
        }

        private final int valore;
        private final String codice;

        public String getCodice() {
            return codice;
        }

        public int getValore() {
            return valore;
        }

        boolean mossaValidaGenerica(ScacchieraGamestate gameState, Mossa m) {
            int colEnd, rowEnd;
            colEnd = m.getEnd().getColumn();
            rowEnd = m.getEnd().getRow();
            Casella casellaEnd = gameState.getScacchiera()[rowEnd][colEnd];
            //fa i calcoli BASE e decide se la mossa è valida di base
            //non sfora scacchiera //b3
            if (colEnd > 7 || colEnd < 0 || rowEnd > 7 || rowEnd < 0)
                return false;
            //non finisce dove c'è altro pezzo stesso colore
            //il colore consiglio di salvarlo dentro oggetto Posizione//es proprietà posizione: enums.Pezzo - ColorePezzo - Riga - Colonna
            return casellaEnd.getColorePezzo() != m.getStart().getColorePezzo();
        }

        //metodo astratto che viene sovrascritto da ogni pezzo
        abstract boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Mossa m);


        public boolean mossaValida(ScacchieraGamestate gameState, Mossa m) {
            return mossaValidaGenerica(gameState, m) && mossaValidaSpecifica(gameState, m);
        }

    boolean isStraightPathClear(ScacchieraGamestate gameState, int rowStart, int colStart, int rowEnd, int colEnd) {
        if (rowStart != rowEnd && colStart != colEnd) return false; // Non è un movimento rettilineo

        int rowStep = Integer.compare(rowEnd, rowStart);
        int colStep = Integer.compare(colEnd, colStart);

        int currentRow = rowStart + rowStep;
        int currentCol = colStart + colStep;

        while (currentRow != rowEnd || currentCol != colEnd) {
            if (gameState.getScacchiera()[currentRow][currentCol].getPezzo() != null) return false;
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }
    boolean isDiagonalPathClear(ScacchieraGamestate gameState, int rowStart, int colStart, int rowEnd, int colEnd) {
        int rowStep = (rowEnd > rowStart) ? 1 : -1;
        int colStep = (colEnd > colStart) ? 1 : -1;

        int currentRow = rowStart + rowStep;
        int currentCol = colStart + colStep;

        while (currentRow != rowEnd && currentCol != colEnd) {
            if (gameState.getScacchiera()[currentRow][currentCol].getPezzo()!= null)
                return false; // C'è un pezzo lungo la diagonale
            currentRow += rowStep;
            currentCol += colStep;
        }
        return true; // Nessun pezzo lungo la diagonale
    }
}

