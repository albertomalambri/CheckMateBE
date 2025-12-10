package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.entities.Partita;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;

import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.model.repositories.PartitaRepo;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.model.repositories.UserRepository;
import com.generation.checkmatebe.utilities.ChessUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.generation.checkmatebe.model.enums.Color.BIANCO;
import static com.generation.checkmatebe.model.enums.Color.NERO;

@Service
public class GameEngineService
{
    @Autowired
    private ScacchieraRepository ScacchieraRepository;

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private UserRepository urepo;

    @Autowired
    private PartitaRepo pRepo;

    public ScacchieraGamestateDTO inizializzaGamestate(String username)
    {
        Partita game = new Partita();
        game.setGiocatoreBianco(username);
        game.setGiocatoreNero(username);
        game.setGame(urepo.findByUsername(username));
        game.setTipoPartita("Solo game");
        pRepo.save(game);
        return gameStateService.inizializzaGamestate(game.getId());
    }

    @Transactional
    public ScacchieraGamestate nextGameState(Long id, MossaDTO dto) //prende come input ScacchieraGamestate,Move
    {
        ScacchieraGamestate currentGameState = ScacchieraRepository.findById(id).get();
        // da = e4 dove e sta per la colonna e 4 per la riga
        StringBuilder da = new StringBuilder();
        StringBuilder a = new StringBuilder();
            if (Character.isLetter(dto.getDa().charAt(0))) {
                da.append(dto.getDa());
                da.reverse();
                a.append(dto.getA());
                a.reverse();

            }
            else{
                da.append(dto.getDa());
                a.append(dto.getA());
            }

        int colStart = ChessUtils.getColumnIndex(da.charAt(1));
        int rowStart = ChessUtils.getRowIndex(da.charAt(0));
        int colEnd = ChessUtils.getColumnIndex(a.charAt(1));
        int rowEnd = ChessUtils.getRowIndex(a.charAt(0));



        Mossa m = new Mossa();
        m.setTurno(dto.getNumero());
        m.setStart(currentGameState.getScacchiera()[rowStart][colStart]);
        m.setEnd(currentGameState.getScacchiera()[rowEnd][colEnd]);
        m.setPezzo(Pezzo.getByCodice(dto.getPezzo().toUpperCase())); //PE,CA,AL,RE,RG,TO
        m.setCattura(dto.isCattura());
        m.setPezzoPromozione(dto.getPezzoPromozione());

        if (m.getStart().getColorePezzo() == currentGameState.getCurrentPlayer() && m.getPezzo().mossaValida(currentGameState, m)) {
            ScacchieraGamestate nextGameState = currentGameState;
            //legare alla casella finale il pezzo in posizione start
            Casella[][] scacchiera = currentGameState.getScacchiera();
            LinkedList<Mossa> previousMoves = currentGameState.getPreviousMoves();
            Pezzo p = m.getPezzo();
            scacchiera[rowEnd][colEnd].setPezzo(p);
            scacchiera[rowEnd][colEnd].setColorePezzo(m.getStart().getColorePezzo());
            scacchiera[rowEnd][colEnd].setGiaMosso(true);
            //settare a null casella di partenza
            scacchiera[rowStart][colStart].svuotaCasella();
            scacchiera[rowStart][colStart].setGiaMosso(true);
            if (m.getPezzoPromozione() != null) {
                scacchiera[rowEnd][colEnd].setPezzo(Pezzo.getByCodice(m.getPezzoPromozione().toUpperCase()));
            }
            Casella reNuovoTurno;
            if (currentGameState.getCurrentPlayer() == BIANCO)
                reNuovoTurno = cercaRe(nextGameState, NERO);
            else
                reNuovoTurno = cercaRe(nextGameState, BIANCO);
            //dopo l'aggiornamento andrà salvato in nextGamestate
            if (!isChecked(currentGameState, cercaRe(currentGameState, currentGameState.getCurrentPlayer()))) {
                nextGameState.setId(currentGameState.getId());
                nextGameState.setScacchiera(scacchiera);
                nextGameState.cambioTurno();
                previousMoves.add(m);
                nextGameState.setPreviousMoves(previousMoves);
                if (isChecked(nextGameState, reNuovoTurno)) {
                    reNuovoTurno.setGiaMosso(true);
                    nextGameState.setCheck(true);
                    if (isCheckMated(nextGameState, reNuovoTurno))
                        nextGameState.setCheckMate(true);
                    else
                        nextGameState.setCheckMate(false);
                } else {
                    nextGameState.setCheck(false);
                    nextGameState.setCheckMate(false);
                }
                if (verificaStallo(nextGameState, scacchiera[rowEnd][colEnd]))
                    nextGameState.setStallo(false);
                else
                    nextGameState.setStallo(true);

                ScacchieraRepository.save(nextGameState);
//                if(nextGameState.getCurrentPlayer()== NERO)
//                    nextGameState(id,gameStateService.mossaAI());
                return nextGameState;
            }

        }
        throw new IllegalArgumentException("Mossa non valida: " + da + " → " + a);
    }


    public boolean isChecked (ScacchieraGamestate gamestate, Casella casellaRe)
        {
            /**
             * Se controllando nella riga verticale e orizzontale del re e trovo una regina o torre avversaria: checked
             * se controllando le diagonali passanti per la posizione del re e trovo una regina o un alfiere avversario come primo pezzo: checked
             * se nelle due caselle diagonali "superiori" trovo un pedone: checked
             * se possibili mosse dei cavalli avversari coincidono con la posizione del re: checked
             * */
            return isAttackedByRookOrQueen(gamestate, casellaRe)
                    || isAttackedByBishopOrQueen(gamestate, casellaRe)
                    || isAttackedByPawn(gamestate, casellaRe)
                    || isAttackedByKnight(gamestate, casellaRe);
        }


    private boolean isAttackedByRookOrQueen(ScacchieraGamestate gamestate, Casella reCasella) {
        int r = reCasella.getRow();
        int c = reCasella.getColumn();
        Casella[][] scacchiera = gamestate.getScacchiera();

        // 🔹 Direzioni: destra, sinistra, su, giù
        int[][] directions = {
                {0, 1},   // destra
                {0, -1},  // sinistra
                {-1, 0},  // su
                {1, 0}    // giù
        };

        for (int[] dir : directions) {
            int i = r + dir[0];
            int j = c + dir[1];

            while (i >= 0 && i < 8 && j >= 0 && j < 8) {
                Casella cella = scacchiera[i][j];
                if (cella.getPezzo() != null) {
                    if ((Math.abs(i-r)==1 || Math.abs(j-c)==1) && controllaPezzo(cella, reCasella, Set.of("RE")))
                        return true;
                    // 🔸 Se è un pezzo nemico e valido → scacco!
                    if (controllaPezzo(cella, reCasella, Set.of("TO", "RG"))) {
                        return true;
                    } else {
                        // 🔸 Se è un pezzo amico → la linea è bloccata
                        break;
                    }
                }
                i += dir[0];
                j += dir[1];
            }
        }

        return false;
    }


    private boolean isAttackedByBishopOrQueen(ScacchieraGamestate gamestate, Casella reCasella) {
        int r = reCasella.getRow();
        int c = reCasella.getColumn();
        Casella[][] scacchiera = gamestate.getScacchiera();

        // 🔹 Direzioni diagonali: alto-destra, alto-sinistra, basso-destra, basso-sinistra
        int[][] directions = {
                {-1, 1},  // alto-destra
                {-1, -1}, // alto-sinistra
                {1, 1},   // basso-destra
                {1, -1}   // basso-sinistra
        };

        for (int[] dir : directions) {
            int i = r + dir[0];
            int j = c + dir[1];

            while (i >= 0 && i < 8 && j >= 0 && j < 8) {
                Casella cella = scacchiera[i][j];
                if (cella.getPezzo() != null) {
                    if (Math.abs(i-r)==1 && Math.abs(j-c)==1 && controllaPezzo(cella, reCasella, Set.of("RE")))
                        return true;
                    // 🔸 Se è un pezzo avversario valido → scacco
                    else if (controllaPezzo(cella, reCasella, Set.of("AL", "RG"))) {
                        return true;
                    } else {
                        // 🔸 Se è un pezzo amico → blocca la direzione
                        break;
                    }
                }
                i += dir[0];
                j += dir[1];
            }
        }

        return false;
    }


    private boolean controllaPezzo(Casella attaccante, Casella reCasella, Set<String> codiciValidi) {
        Pezzo p = attaccante.getPezzo();
        return p != null
                && attaccante.getColorePezzo()!=reCasella.getColorePezzo()
                && codiciValidi.contains(p.getCodice());
    }





    private boolean isAttackedByPawn(ScacchieraGamestate gamestate, Casella reCasella) {
        int r = reCasella.getRow();
        int c = reCasella.getColumn();
        Casella[][] scacchiera = gamestate.getScacchiera();
        Color reColor = reCasella.getColorePezzo();

        int direction = (reColor == Color.BIANCO) ? -1 : 1;

        for (int dc : new int[]{-1, 1}) {
            int nr = r + direction;
            int nc = c + dc;
            if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                Casella cella = scacchiera[nr][nc];
                Pezzo p = cella.getPezzo();
                if (p != null && !cella.getColorePezzo().equals(reColor) && p.getCodice().equals("PE"))
                    return true;
            }
        }

        return false;
    }


    private boolean isAttackedByKnight(ScacchieraGamestate gamestate, Casella reCasella) {
        int r = reCasella.getRow();
        int c = reCasella.getColumn();
        Casella[][] scacchiera = gamestate.getScacchiera();
        Color reColor = reCasella.getColorePezzo();

        int[][] moves = {
                {-2, -1}, {-2, +1}, {-1, -2}, {-1, +2},
                {+1, -2}, {+1, +2}, {+2, -1}, {+2, +1}
        };

        for (int[] m : moves) {
            int nr = r + m[0];
            int nc = c + m[1];
            if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                Casella cella = scacchiera[nr][nc];
                Pezzo p = cella.getPezzo();
                if (p != null && !cella.getColorePezzo().equals(reColor) && p.getCodice().equals("CA"))
                    return true;
            }
        }

        return false;
    }



    public boolean isCheckMated(ScacchieraGamestate gamestate, Casella casella)
    {
        /**
         * Controllo caselle adiacenti is checked od occupate da altri pezzi
         * controllo se nelle posibili mosse dei pezzi amici possono catturare il pezzo avversario
         * controllo se pezzi amici si possono mettere nella traiettoria
         * */
        Casella[][] scacchiera = gamestate.getScacchiera();
        int row = casella.getRow();
        int column = casella.getColumn();
        Casella casellaScacco = trovaPezzoCheDaScacco(gamestate,casella);

        if ((row+1<8 && (scacchiera[row+1][column].getPezzo()==null || scacchiera[row+1][column].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row+1][column].getColorePezzo();
            scacchiera[row+1][column].setColorePezzo(gamestate.getCurrentPlayer());
            if ( !isChecked(gamestate,scacchiera[row+1][column])) {
                if (scacchiera[row+1][column].getColorePezzo()==BIANCO) {
                    scacchiera[row + 1][column].setColorePezzo(NERO);
                    return false;
                }
                else {
                    scacchiera[row + 1][column].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row+1][column].setColorePezzo(colore);
        }
        if ((row-1>=0 && (scacchiera[row-1][column].getPezzo()==null || scacchiera[row-1][column].getColorePezzo() != casella.getColorePezzo()))) {
            Color colore = scacchiera[row-1][column].getColorePezzo();
            scacchiera[row - 1][column].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row - 1][column])) {
                if (scacchiera[row - 1][column].getColorePezzo() == BIANCO) {
                    scacchiera[row - 1][column].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row - 1][column].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row-1][column].setColorePezzo(colore);
        }
        if ((column+1<8 && (scacchiera[row][column+1].getPezzo()==null || scacchiera[row][column+1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row][column+1].getColorePezzo();
            scacchiera[row ][column +1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row ][column+1])) {
                if (scacchiera[row ][column+1].getColorePezzo() == BIANCO) {
                    scacchiera[row ][column+1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row ][column+1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row][column+1].setColorePezzo(colore);
        }
        if ((column-1>=0 && (scacchiera[row][column-1].getPezzo()==null || scacchiera[row][column-1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row][column-1].getColorePezzo();
            scacchiera[row][column - 1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row][column - 1])) {
                if (scacchiera[row][column - 1].getColorePezzo() == BIANCO) {
                    scacchiera[row][column - 1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row][column - 1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row][column-1].setColorePezzo(colore);
        }
        if ((row+1<8 && column-1>=0 && (scacchiera[row+1][column-1].getPezzo()==null || scacchiera[row+1][column-1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row+1][column-1].getColorePezzo();
            scacchiera[row + 1][column - 1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row + 1][column - 1])) {
                if (scacchiera[row + 1][column - 1].getColorePezzo() == BIANCO) {
                    scacchiera[row + 1][column - 1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row + 1][column - 1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row+1][column-1].setColorePezzo(colore);
        }
        if ((row+1<8 && column+1<8 && (scacchiera[row+1][column+1].getPezzo()==null || scacchiera[row+1][column+1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row+1][column+1].getColorePezzo();
            scacchiera[row + 1][column + 1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row + 1][column + 1])) {
                if (scacchiera[row + 1][column + 1].getColorePezzo() == BIANCO) {
                    scacchiera[row + 1][column + 1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row + 1][column + 1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row+1][column+1].setColorePezzo(colore);
        }
        if ((row-1>=0 && column+1<8 && (scacchiera[row-1][column+1].getPezzo()==null || scacchiera[row-1][column+1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row-1][column+1].getColorePezzo();
            scacchiera[row - 1][column + 1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row - 1][column + 1])) {
                if (scacchiera[row - 1][column + 1].getColorePezzo() == BIANCO) {
                    scacchiera[row - 1][column + 1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row - 1][column + 1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row-1][column+1].setColorePezzo(colore);
        }
        if ((row-1>=0 && column-1>=0 && (scacchiera[row-1][column-1].getPezzo()==null || scacchiera[row-1][column-1].getColorePezzo() != casella.getColorePezzo()) )) {
            Color colore = scacchiera[row-1][column-1].getColorePezzo();
            scacchiera[row - 1][column - 1].setColorePezzo(gamestate.getCurrentPlayer());
            if (!isChecked(gamestate, scacchiera[row - 1][column - 1])) {
                if (scacchiera[row - 1][column - 1].getColorePezzo() == BIANCO) {
                    scacchiera[row - 1][column - 1].setColorePezzo(NERO);
                    return false;
                } else {
                    scacchiera[row - 1][column - 1].setColorePezzo(BIANCO);
                    return false;
                }
            }
            scacchiera[row-1][column-1].setColorePezzo(colore);
        }

        if (controllaPezzoCheProteggeDaScacco(gamestate, casella, casellaScacco) || (isChecked(gamestate,casellaScacco) && trovaPezzoCheDaScacco(gamestate,casellaScacco) != null))
            return false;
        return true;
    }

    public Casella cercaRe(ScacchieraGamestate gamestate, Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamestate.getScacchiera()[i][j].getPezzo()!= null && gamestate.getScacchiera()[i][j].getPezzo().getCodice().equals("RE") && gamestate.getScacchiera()[i][j].getColorePezzo()==color)
                    return gamestate.getScacchiera()[i][j];
            }
        }
        return null;
    }

    public Casella trovaPezzoCheDaScacco(ScacchieraGamestate gamestate, Casella casella) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamestate.getScacchiera()[i][j].getPezzo()!=null && gamestate.getScacchiera()[i][j].getColorePezzo()!= null && gamestate.getScacchiera()[i][j].getColorePezzo()!=gamestate.getCurrentPlayer() && !gamestate.getScacchiera()[i][j].getPezzo().getCodice().equals("RE")) {
                   Mossa mossa = new Mossa();
                   mossa.setStart(gamestate.getScacchiera()[i][j]);
                   mossa.setEnd(casella);
                   mossa.setPezzo(gamestate.getScacchiera()[i][j].getPezzo());
                   if (mossa.getPezzo().mossaValida(gamestate,mossa))
                       return gamestate.getScacchiera()[i][j];
                }
            }
        }
        return null;
    }

    public boolean controllaPezzoCheProteggeDaScacco(ScacchieraGamestate gamestate, Casella casellaRe, Casella casellaScacco) {

        Casella [][] scacchiera = gamestate.getScacchiera();
        List<Casella> caselle = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (scacchiera[i][j].getPezzo()== null) {
                    scacchiera[i][j].setPezzo(Pezzo.PEDONE);
                    scacchiera[i][j].setColorePezzo(casellaRe.getColorePezzo());
                    if (!isChecked(gamestate,casellaRe)) {
                        scacchiera[i][j].svuotaCasella();
                        scacchiera[i][j].setColorePezzo(casellaScacco.getColorePezzo());
                        if (trovaPezzoCheDaScacco(gamestate, scacchiera[i][j])!=null) {
                            scacchiera[i][j].svuotaCasella();
                            return true;
                        }
                    }
                    scacchiera[i][j].svuotaCasella();
                }
            }
        }
        return false;
    }
    //Return true se trova una mossa possibile, sennò false e quindi è stallo
    public boolean verificaStallo(ScacchieraGamestate gamestate, Casella casellaMossaQuestoTurno) {
        Casella [][] scacchiera = gamestate.getScacchiera();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (scacchiera[i][j].getPezzo()==null) {
                    scacchiera[i][j].setColorePezzo(casellaMossaQuestoTurno.getColorePezzo());
                    if (isChecked(gamestate,scacchiera[i][j])) {
                        if (casellaMossaQuestoTurno.getColorePezzo()==BIANCO)
                            scacchiera[i][j].setColorePezzo(NERO);
                        else
                            scacchiera[i][j].setColorePezzo(BIANCO);
                        if (!isChecked(gamestate,scacchiera[i][j])) {
                            scacchiera[i][j].setColorePezzo(null);
                            return true;
                        }
                    }
                    scacchiera[i][j].setColorePezzo(null);

                } else if (scacchiera[i][j].getColorePezzo()==casellaMossaQuestoTurno.getColorePezzo()) {
                    if (isChecked(gamestate, scacchiera[i][j]))
                        return true;
                }
            }
        }
        return false;
    }

}

