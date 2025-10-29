package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;

import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.utilities.ChessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEngineService
{
    @Autowired
    ScacchieraRepository ScacchieraRepository;

    @Autowired
    GameStateService gameStateService;


    public ScacchieraGamestate inizializzaGamestate()
    {
        return gameStateService.inizializzaGamestate();
    }

    public ScacchieraGamestate nextGameState(Long id, MossaDTO dto) //prende come input ScacchieraGamestate,Move
    {
        ScacchieraGamestate currentGameState = ScacchieraRepository.getReferenceById(id);
        // da = e4 dove e sta per la colonna e 4 per la riga
        int colStart = ChessUtils.getColumnIndex(dto.getDa().charAt(0));
        int rowStart = ChessUtils.getRowIndex(dto.getDa().charAt(1));
        int colEnd = ChessUtils.getColumnIndex(dto.getA().charAt(0));
        int rowEnd = ChessUtils.getRowIndex(dto.getA().charAt(1));

        Mossa m = new Mossa();
        m.setTurno(dto.getTurno());
        m.setStart(currentGameState.getScacchiera()[rowStart][colStart]);
        m.setEnd(currentGameState.getScacchiera()[rowEnd][colEnd]);
        m.setPezzo(Pezzo.getByCodice(dto.getPezzo())); //PE,CA,AL,RE,RG,TO
        m.setCattura(dto.isCattura());
        if(m.getPezzo().mossaValida(currentGameState,m))
        {
            ScacchieraGamestate nextGameState = currentGameState;
            //legare alla casella finale il pezzo in posizione start
            Casella[][] scacchiera = currentGameState.getScacchiera();
            Pezzo p = m.getPezzo();
            scacchiera[rowEnd][colEnd].setPezzo(p);
            //settare a null casella di partenza
            scacchiera[rowStart][colStart].svuotaCasella();
            //dopo l'aggiornamento andrà salvato in nextGamestate
            nextGameState.setScacchiera(scacchiera);
            nextGameState.cambioTurno();
            nextGameState.getPreviousMoves().add(m);
            ScacchieraRepository.save(nextGameState);
            return nextGameState;
        }
        throw new IllegalArgumentException("Mossa non valida: " + dto.getDa() + " → " + dto.getA());
    }
}

//public boolean isChecked (ScacchieraGamestate gamestate, Casella casella)
//    {
//        /**
//         * se controllando nella riga verticale e orizzontale del re e trovo una regina o torre avversaria: checked
//         * se controllando le diagonali passanti per la posizione del re e trovo una regina o un alfiere avversario come primo pezzo: checked
//         * se nelle due caselle diagonali "superiori" trovo un pedone: checked
//         * se possibili mosse dei cavalli avversari coincidono con la posizione del re: checked
//         * */
//
//
//
//return isAttackedByRookOrQueen(gamestate, casella)
//                || isAttackedByBishopOrQueen(gamestate, casella)
//                || isAttackedByPawn(gamestate, casella)
//                || isAttackedByKnight(gamestate, casella);
//
//    }
//    private boolean isAttackedByRookOrQueen(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//            //recupero la posizione
//            int r,c;
//            Casella[][] scacchiera= gamestate.getScacchiera();
//            r = checkCasella.getRow();
//            c = checkCasella.getColumn();
//
//            if(checkCasella.getPezzo()!=null) {
//                Color pieceColor = checkCasella.getColorePezzo();
//                //for per scorrere a destra
//                for(int j=checkCasella.getColumn()+1;j<8;j++)
//                    if(scacchiera[r][j].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//
//                // for per scorrere a sinistra
//                for(int j=checkCasella.getColumn()+1;j>=0;j--)
//                    if(scacchiera[r][j].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//                //for per scorrere a sopra
//                for(int i=checkCasella.getRow();i>=0;i--)
//                    if(scacchiera[i][c].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//                //for per scorrere a sotto
//                for(int i=checkCasella.getRow();i<8;i++)
//                    if(scacchiera[i][c].getPezzo()!=null)
//                    {
//                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
//                    }
//            }
//        return false;
//    }
//    private boolean isAttackedByBishopOrQueen(ScacchieraGamestate gamestate, Casella checkCasella){
//        //recupero la posizione
//        int r,c;
//        Casella[][] scacchiera= gamestate.getScacchiera();
//        r = checkCasella.getRow();
//        c = checkCasella.getColumn();
//
//        // controllo diagonale alto a destra
//        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
//            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//
//        // controllo diagonale alto a sinistra
//        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
//            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//
//        // controllo diagonale basso a destra
//        for(int i=checkCasella.getRow();i<8;i++)//for per righe
//            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//        // controllo diagonale basso a sinistra
//        for(int i=checkCasella.getRow();i<8;i++)//for per righe
//            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
//                if(scacchiera[i][j].getPezzo()!=null)
//                {
//                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
//                }
//        return false;
//    }
//
//    private boolean controllaPezzo(Pezzo p, Casella checkCasella, Set<String> attackingPieces)
//    {
//        return(p.getColor()!=checkCasella.getPezzo().getColor() && attackingPieces.contains(p.getNome()));
//    }
//
//
//
//    private boolean isAttackedByPawn(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//        return false;
//    }
//    private boolean isAttackedByKnight(ScacchieraGamestate gamestate, Casella checkCasella)
//    {
//        return false;
//    }
//
//    public boolean isCheckMated()
//    {
//        /**
//         * controllo caselle adiacenti is checked o occupate da altri pezzi
//         * controllo se nelle posibili mosse dei pezzi amici possono catturare il pezzo avversario
//         * controllo se pezzi amici si possono mettere nella traiettoria
//         * */
//        return false;
//
//    }
//}
