package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.enums.Color;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ScacchieraGamestate
{
    @Id
    private Long id;
    private List<String> piecesPosition;
    @NotNull
    private Color currentPlayer;
    private List<String> previousMoves;
}
