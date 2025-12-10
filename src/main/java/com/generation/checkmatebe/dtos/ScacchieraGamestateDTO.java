package com.generation.checkmatebe.dtos;

import com.generation.checkmatebe.model.entities.Casella;
import com.generation.checkmatebe.model.entities.Mossa;
import com.generation.checkmatebe.model.enums.Color;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ScacchieraGamestateDTO {
    private Long id;
    private List<CasellaDTO> scacchiera;
    private Color currentPlayer;
    private boolean isCheck;
    private boolean isCheckMate;
    private boolean isStallo;
}
