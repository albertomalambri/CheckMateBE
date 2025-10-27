package com.generation.checkmatebe.model.enums;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.utilities.ChessUtils;

public enum Pezzo {
    PEDONE(1,"PE") {
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella casellaStart, Mossa m) {
            //solo controlli specifici per pedone
            int colStart= ChessUtils.getColumnIndex(m.getStart().charAt(0));
            int rowStart= ChessUtils.getRowIndex(m.getStart().charAt(1));
            int colEnd= ChessUtils.getColumnIndex(m.getEnd().charAt(0));
            int rowEnd= ChessUtils.getRowIndex(m.getEnd().charAt(1));
            Casella casellaEnd = gameState.getScacchiera()[rowEnd][colEnd];
            //controllo se bianco è stato mosso
            if(rowStart==6)
                return((casellaEnd.getNomePezzo()==null)&&(colEnd==colStart)&&(rowEnd==rowStart-2 || rowEnd == rowStart-1));
            if(rowStart==1)
                return((casellaEnd.getNomePezzo()==null)&&(colEnd==colStart)&&(rowEnd==rowStart+2 || rowEnd == rowStart+1));
            //METODO CanEAT
            //caso pedone bianco
            if(casellaStart.getColorePezzo()==Color.BIANCO)
                if (colEnd==colStart-1 && rowEnd==rowStart-1 || colEnd==colStart+1 && rowEnd==rowStart-1)
                {
                    return (casellaEnd.getColorePezzo()!=casellaStart.getColorePezzo());
                }
            //caso pedone nero
            if(casellaStart.getColorePezzo()==Color.NERO)
                if (colEnd==colStart-1 && rowEnd==rowStart+1 || colEnd==colStart+1 && rowEnd==rowStart+1)
                {
                    return casellaEnd.getColorePezzo() != casellaStart.getColorePezzo();
                }
            return false;
        }

    },
    CAVALLO(3,"CA") {
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella partenza, Mossa m) {
            //solo controlli specifici per cavallo
            return false;
        }
    },
    ALFIERE(3,"AL"){
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella partenza, Mossa m) {
            //solo controlli specifici per alfiere
            return false;
        }
    },
    TORRE(5,"TO"){
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella partenza, Mossa m) {
            //solo controlli specifici per torre
            return false;
        }
    },
    REGINA(9,"RG"){
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella partenza, Mossa m) {
            //solo controlli specifici per regina
            return false;
        }
    },
    RE(0,"RE"){
        @Override
        boolean mossaValidaSpecifica(ScacchieraGamestate gameState, Casella partenza, Mossa m) {
            //solo controlli specifici per re
            return false;
        }
    };


    public static Pezzo getByCodice(String codice)
    {
        for(Pezzo p : Pezzo.values())
            if(p.getCodice().equalsIgnoreCase(codice))
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

    boolean mossaValidaGenerica(ScacchieraGamestate gameState, Casella casellaStart, Mossa m)
    {
        int colEnd, rowEnd;
        colEnd= ChessUtils.getColumnIndex(m.getEnd().charAt(0));
        rowEnd= ChessUtils.getRowIndex(m.getEnd().charAt(1));
        Casella casellaEnd = gameState.getScacchiera()[rowEnd][colEnd];
        //fa i calcoli BASE e decide se la mossa è valida di base
        //non sfora scacchiera //b3
        if(colEnd > 7 || colEnd <0 || rowEnd > 7 || rowEnd < 0)
            return false;
        //non finisce dove c'è altro pezzo stesso colore
        //il colore consiglio di salvarlo dentro oggetto Posizione//es proprietà posizione: enums.Pezzo - ColorePezzo - Riga - Colonna
        return casellaEnd.getColorePezzo() != casellaStart.getColorePezzo();
    }

    //metodo astratto che viene sovrascritto da ogni pezzo
    abstract boolean mossaValidaSpecifica(ScacchieraGamestate gameState,Casella partenza,Mossa m);


    public boolean mossaValida(ScacchieraGamestate gameState,Casella partenza,Mossa m)
    {
        return mossaValidaGenerica(gameState,partenza,m) && mossaValidaSpecifica(gameState,partenza,m);
    }
}
