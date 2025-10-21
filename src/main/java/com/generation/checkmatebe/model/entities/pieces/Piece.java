package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.inGame;
import com.generation.checkmatebe.model.entities.Casella;
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

    protected Casella posizione;  // classe custom con riga/colonna

    public abstract List<Casella> calcolaMossePossibili(Casella casella);

    public abstract boolean canEat(Piece other);

}
