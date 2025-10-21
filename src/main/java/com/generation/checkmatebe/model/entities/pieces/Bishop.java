package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.model.Position;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Bishop extends Piece
{
    @Id
    private Long id;

    @Override
    public List<Position> calcolaMossePossibili(Position position)
    {
        List<Position> pos = new ArrayList<>();
        for (int i=1; i<8; i++)
        {
            pos.add(new Position(position.getRow()+i, position.getColumn()+i));
            pos.add(new Position(position.getRow()+i, position.getColumn()-i));
            pos.add(new Position(position.getRow()-i, position.getColumn()+i));
            pos.add(new Position(position.getRow()-i, position.getColumn()-i));

        }
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }
}
