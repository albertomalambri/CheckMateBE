package com.generation.checkmatebe;

import com.generation.checkmatebe.model.entities.ScacchieraGamestate;
import com.generation.checkmatebe.services.GameEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckMateBeApplicationTests {

    @Autowired
    GameEngine engine;

    @Test
    void contextLoads() {

        ScacchieraGamestate scacchiera = engine.inizializzaGamestate();
        scacchiera.getScacchiera()[0][3].getPezzo().setPosizione(scacchiera.getScacchiera()[4][4]);
        System.out.println(scacchiera.getScacchiera()[4][4].getPezzo().calcolaMossePossibili().stream().map(casella -> casella.getNomeCasella()).toList());
        scacchiera.getScacchiera()[0][3].svuotaCasella();
//        System.out.println(scacchiera.getScacchiera()[0][1].getPezzo()==null ? "si non ci sono" : "ci sono");

    }

}
