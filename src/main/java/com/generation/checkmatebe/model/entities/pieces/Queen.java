package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.Position;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Queen extends Piece
{
    @Id
    private Long id;

    @Override
    public List<Position> calcolaMossePossibili()
    {
        return List.of();
    }

    @Override
    public boolean canEat(Piece other)
    {
        return false;
    }
}
