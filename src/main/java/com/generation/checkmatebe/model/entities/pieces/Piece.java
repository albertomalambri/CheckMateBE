package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.enums.Color;
import com.generation.checkmatebe.enums.inGame;
import com.generation.checkmatebe.model.Position;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@MappedSuperclass
public abstract class Piece
{
    @NotNull
    private Color color;
    @NotNull
    private inGame inGame;

    protected Position posizione;  // classe custom con riga/colonna

    public abstract List<Position> calcolaMossePossibili(Position position);

    public abstract boolean canEat(Piece other);

}
