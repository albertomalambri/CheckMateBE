package com.generation.checkmatebe;

import com.generation.checkmatebe.dtos.PieceDTO;
import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.services.GameEngine;
import com.generation.checkmatebe.utilities.ChessUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CheckMateBeApplicationTests {

    @Autowired
    GameEngine engine;

    @Test
    void contextLoads() {

        ScacchieraGamestate scacchiera = engine.inizializzaGamestate();
//        scacchiera.getScacchiera()[0][3].getPezzo().setPosizione(scacchiera.getScacchiera()[4][4]);
//        System.out.println(scacchiera.getScacchiera()[4][4].getPezzo().calcolaMossePossibili().stream().map(casella -> casella.getNomeCasella()).toList());
//        scacchiera.getScacchiera()[0][3].svuotaCasella();
//        System.out.println(scacchiera.getScacchiera()[0][1].getPezzo()==null ? "si non ci sono" : "ci sono");
        List<PieceDTO> pieces = engine.findAllAsDto(scacchiera.getId());
        for (PieceDTO p : pieces)
            if (p.getPosizione().equals("b1"))
                for (String s : p.getMossePossibili())
                    System.out.println(s);

//        System.out.println(ChessUtils.positionToString(0, 0)); // deve stampare "a1"
//        System.out.println(ChessUtils.positionToString(1, 3)); // deve stampare "d2"
//        System.out.println(ChessUtils.positionToString(7, 7)); // deve stampare "h8"
    }

}
