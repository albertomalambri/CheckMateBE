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
public class Knight extends Piece
{
    @Id
    private Long id;


    @Override
    public List<Position> calcolaMossePossibili(Position position) {
        List<Position> pos = new ArrayList<>();
        pos.add(new Position(position.getRow()+2,position.getColumn()+1));
        pos.add(new Position(position.getRow()+2,position.getColumn()-1));
        pos.add(new Position(position.getRow()-2,position.getColumn()+1));
        pos.add(new Position(position.getRow()-2,position.getColumn()-1));

        pos.add(new Position(position.getRow()+1,position.getColumn()+2));
        pos.add(new Position(position.getRow()-1,position.getColumn()+2));
        pos.add(new Position(position.getRow()+1,position.getColumn()-2));
        pos.add(new Position(position.getRow()-1,position.getColumn()-2));

        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return this.getColor()!=other.getColor();
    }
}
