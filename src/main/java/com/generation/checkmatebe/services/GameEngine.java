package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PieceDTO;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.inGame;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.model.entities.pieces.*;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.utilities.ChessUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GameEngine
{
    @Autowired
    ScacchieraRepository repo;


    public ScacchieraGamestate inizializzaGamestate()
    {
        ScacchieraGamestate scacchiera = new ScacchieraGamestate();
        Casella[][] caselle = new Casella[8][8];
        for (int i = 0; i < 8; i++) { //righe
            for (int j = 0; j < 8; j++) { //colonne
                Casella casella = new Casella(i,j);
                casella.setColor(controllaColoreCasella(j,i));
                casella.setGameState(scacchiera);
                caselle[i][j]=casella;

                if (i<=1 || i>=6) {
                    if (i==1 || i==6) {
                        Pawn pedone = new Pawn();
                        pedone.setPosizione(casella);
                        pedone.setColor(controlloColorePezzo(i));
                        casella.setSalvataggio(pedone);
                    }
                    else {
                        switch (""+ChessUtils.positionToString(i,j).charAt(0)) {
                            case "a", "h" -> {
                                Rook rook = new Rook();
                                rook.setPosizione(casella);
                                rook.setColor(controlloColorePezzo(i));
                                casella.setSalvataggio(rook);
                            }
                            case "b","g" -> {
                                Knight knight = new Knight();
                                knight.setPosizione(casella);
                                knight.setColor(controlloColorePezzo(i));
                                casella.setSalvataggio(knight);
                            }
                            case "c","f" -> {
                                Bishop bishop = new Bishop();
                                bishop.setPosizione(casella);
                                bishop.setColor(controlloColorePezzo(i));
                                casella.setSalvataggio(bishop);
                            }
                            case "d" -> {
                                Queen queen = new Queen();
                                queen.setPosizione(casella);
                                queen.setColor(controlloColorePezzo(i));
                                casella.setSalvataggio(queen);
                            }
                            case "e" -> {
                                King king = new King();
                                king.setPosizione(casella);
                                king.setColor(controlloColorePezzo(i));
                                casella.setSalvataggio(king);
                            }
                        }
                    }
                }
            }
        }
        scacchiera.setScacchiera(caselle);
        repo.save(scacchiera);
        return scacchiera;
    }

    private Color controlloColorePezzo(int row) {
        if (row<=1)
            return Color.BIANCO;
        else
            return Color.NERO;
    }

    private Color controllaColoreCasella(int column, int row) {
        if (column%2==1) {
            if (row%2==0)
                return Color.BIANCO;
            else
                return Color.NERO;
        }
        else {
            if (row%2==0)
                return Color.NERO;
            else
                return Color.BIANCO;
        }
    }

    public List<PieceDTO> findAllAsDto(Long id) {
        ScacchieraGamestate gameState = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scacchiera non trovata"));
        Casella[][] scacchiera = gameState.getScacchiera();
        List<PieceDTO> tuttiDto = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                scacchiera[i][j].setGameState(gameState);
                if (scacchiera[i][j].getNomePezzo() != null) {
                    scacchiera[i][j].setPezzo(convertitorePezzi(scacchiera[i][j]));
                }
            }

        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (scacchiera[i][j].getNomePezzo()!=null && scacchiera[i][j].getPezzo().getColor() == gameState.getCurrentPlayer()) {
                    List<String> pos = new ArrayList<>();
                    PieceDTO piece = new PieceDTO();
                    piece.setPosizione(scacchiera[i][j].getNomeCasella());

                    for (Casella c : scacchiera[i][j].getPezzo().calcolaMossePossibili())
                        pos.add(c.getNomeCasella());
                    //ChessUtils.positionToString(ChessUtils.getColumnIndex(c.getNomeCasella().charAt(0)), Integer.parseInt(""+c.getNomeCasella().charAt(1)))
                    piece.setMossePossibili(pos);

                    tuttiDto.add(piece);
                }
            }

        }
        return tuttiDto;
    }

    private Piece convertitorePezzi(Casella casella) {
        String[] split = casella.getNomePezzo().split("_");
        switch (split[0]) {
            case "bishop" -> {
                Bishop bishop = new Bishop();
                bishop.setPosizione(casella);
                if (split[1].equals("n"))
                    bishop.setColor(Color.NERO);
                else
                    bishop.setColor(Color.BIANCO);
                bishop.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(bishop);
                return bishop;
            }
            case "king" -> {
                King king = new King();
                king.setPosizione(casella);
                if (split[1].equals("n"))
                    king.setColor(Color.NERO);
                else
                    king.setColor(Color.BIANCO);
                king.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(king);
                return king;}
            case "knight" -> {
                Knight knight = new Knight();
                knight.setPosizione(casella);
                if (split[1].equals("n"))
                    knight.setColor(Color.NERO);
                else
                    knight.setColor(Color.BIANCO);
                knight.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(knight);
                return knight;}
            case "pawn" -> {
                Pawn pawn = new Pawn();
                pawn.setPosizione(casella);
                if (split[1].equals("n"))
                    pawn.setColor(Color.NERO);
                else
                    pawn.setColor(Color.BIANCO);
                pawn.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(pawn);
                return pawn;}
            case "queen" -> {
                Queen queen = new Queen();
                queen.setPosizione(casella);
                if (split[1].equals("n"))
                    queen.setColor(Color.NERO);
                else
                    queen.setColor(Color.BIANCO);
                queen.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(queen);
                return queen;}
            case "rook" -> {
                Rook rook = new Rook();
                rook.setPosizione(casella);
                if (split[1].equals("n"))
                    rook.setColor(Color.NERO);
                else
                    rook.setColor(Color.BIANCO);
                rook.setGiaMosso(Boolean.parseBoolean(split[2]));
                casella.setSalvataggio(rook);
                return rook;}
            default -> {return null;}
        }
    }

//    private Piece creaPezzoIniziale(int row, int col) {
//        // pedoni
//        if (row == 1)
//            return new Pawn();
//        if (row == 6)
//            return new Pawn();
//
//        // pezzi principali
//        if (row == 0 || row == 7) {
//            Color color = (row == 0) ? Color.BIANCO : Color.NERO;
//            return switch (col) {
//                case 0, 7 -> new Rook();
//                case 1, 6 -> new Knight();
//                case 2, 5 -> new Bishop();
//                case 3 -> new Queen();
//                case 4 -> new King();
//                default -> null;
//            };
//        }
//        return null;
//    }

    public PieceDTO secondoGamestate(Long id, MossaDTO dto) //prende come input ScacchieraGamestate,Move
    {
        ScacchieraGamestate gameState = repo.getReferenceById(id);
        gameState.cambioTurno();
        //pezzo viene mangiato?
        //mossa fattibile o non fattibile
        return new PieceDTO();
    }

    public boolean isChecked (ScacchieraGamestate gamestate, Casella casella, Color pieceColor)
    {
        /**
         * se controllando nella riga verticale e orizzontale del re e trovo una regina o torre avversaria: checked
         * se controllando le diagonali passanti per la posizione del re e trovo una regina o un alfiere avversario come primo pezzo: checked
         * se nelle due caselle diagonali "superiori" trovo un pedone: checked
         * se possibili mosse dei cavalli avversari coincidono con la posizione del re: checked
         * */
        return isAttackedByRookOrQueen(gamestate, casella)
                || isAttackedByBishopOrQueen(gamestate, casella)
                || isAttackedByPawn(gamestate, casella)
                || isAttackedByKnight(gamestate, casella);

    }
    private boolean isAttackedByRookOrQueen(ScacchieraGamestate gamestate, Casella checkCasella){
            //recupero la posizione
            int r,c;
            Casella[][] scacchiera= gamestate.getScacchiera();
            r = checkCasella.getRow();
            c = checkCasella.getColumn();

            if(checkCasella.getPezzo()!=null) {
                Color pieceColor = checkCasella.getPezzo().getColor();
                //for per scorrere a destra
                for(int j=checkCasella.getColumn()+1;j<8;j++)
                    if(scacchiera[r][j].getPezzo()!=null)
                    {
                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
                    }

                // for per scorrere a sinistra
                for(int j=checkCasella.getColumn()+1;j>=0;j--)
                    if(scacchiera[r][j].getPezzo()!=null)
                    {
                        return controllaPezzo(scacchiera[r][j].getPezzo(),checkCasella, Set.of("rook", "queen"));
                    }
                //for per scorrere a sopra
                for(int i=checkCasella.getRow();i>=0;i--)
                    if(scacchiera[i][c].getPezzo()!=null)
                    {
                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
                    }
                //for per scorrere a sotto
                for(int i=checkCasella.getRow();i<8;i++)
                    if(scacchiera[i][c].getPezzo()!=null)
                    {
                        return controllaPezzo(scacchiera[i][c].getPezzo(),checkCasella, Set.of("rook", "queen"));
                    }
            }
        return false;
    }
    /**
         0 1 2 3 4 5 6 7

     0   r n b . k b n r
     1   p p p p p p p p
     2   . . . . . . q .
     3   . . . . . . . .
     4   . . . . . . . .
     5   . . . K . . . .
     6   . . . . . . . .
     7   . . . . . . . .


     */



    private boolean isAttackedByBishopOrQueen(ScacchieraGamestate gamestate, Casella checkCasella){
        //recupero la posizione
        int r,c;
        Casella[][] scacchiera= gamestate.getScacchiera();
        r = checkCasella.getRow();
        c = checkCasella.getColumn();

        // controllo diagonale alto a destra
        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
                if(scacchiera[i][j].getPezzo()!=null)
                {
                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
                }

        // controllo diagonale alto a sinistra
        for(int i=checkCasella.getRow();i>=0;i--)//for per righe
            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
                if(scacchiera[i][j].getPezzo()!=null)
                {
                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
                }

        // controllo diagonale basso a destra
        for(int i=checkCasella.getRow();i<8;i++)//for per righe
            for(int j=checkCasella.getColumn();j<8;j++)//for per le colonne
                if(scacchiera[i][j].getPezzo()!=null)
                {
                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
                }
        // controllo diagonale basso a sinistra
        for(int i=checkCasella.getRow();i<8;i++)//for per righe
            for(int j=checkCasella.getColumn();j>=0;j--)//for per le colonne
                if(scacchiera[i][j].getPezzo()!=null)
                {
                    return controllaPezzo(scacchiera[i][j].getPezzo(),checkCasella, Set.of("bishop", "queen"));
                }
        return false;
    }

    private boolean controllaPezzo(Piece p, Casella checkCasella, Set<String> attackingPieces)
    {
        return(p.getColor()!=checkCasella.getPezzo().getColor() && attackingPieces.contains(p.getNome()));
    }



    private boolean isAttackedByPawn(ScacchieraGamestate gamestate, Casella checkCasella){
        return false;
    }
    private boolean isAttackedByKnight(ScacchieraGamestate gamestate, Casella checkCasella){
        return false;
    }

    public boolean isCheckMated()
    {
        /**
         * controllo caselle adiacenti is checked o occupate da altri pezzi
         * controllo se nelle posibili mosse dei pezzi amici possono catturare il pezzo avversario
         * controllo se pezzi amici si possono mettere nella traiettoria
         * */
        return false;

    }
}
