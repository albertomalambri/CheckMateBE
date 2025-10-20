package com.generation.checkmatebe.model.entities.pieces;

import com.generation.checkmatebe.enums.Color;
import com.generation.checkmatebe.model.Position;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pawn extends Piece
{
    @Id
    private Long id;



    @Override
    public List<Position> calcolaMossePossibili(Position position)
    {
        List<Position> pos = new ArrayList<>();
        if (this.getColor().equals(Color.BIANCO)) {
            if (position.getRow()!=1)
                pos.add(new Position(position.getRow() + 1, position.getColumn()));
            else {
                pos.add(new Position(position.getRow() + 1, position.getColumn()));
                pos.add(new Position(position.getRow() + 2, position.getColumn()));
            }
        }
        else if (this.getColor().equals(Color.NERO)) {
            if (position.getRow()!=6)
                pos.add(new Position(position.getRow() - 1, position.getColumn()));

            else {
                pos.add(new Position(position.getRow() - 1, position.getColumn()));
                pos.add(new Position(position.getRow() - 2, position.getColumn()));
            }
        }
        return pos;
    }

    @Override
    public boolean canEat(Piece other)
    {
        return false;
    }
}

