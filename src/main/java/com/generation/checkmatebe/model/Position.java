package com.generation.checkmatebe.model;

import com.generation.checkmatebe.enums.Color;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position
{
    private final int row;     // da 1 a 8
    private final int column;
    // da 1 a 8 (oppure 'a'–'h' se vuoi usare lettere)

    public Position(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    //Delta sta per variazione o spostamento

    public Position avanti(int delta)
    {
        return new Position(row + delta, column);
    }

    public Position diagonale(int deltaRow, int deltaColumn)
    {
        return new Position(row + deltaRow, column + deltaColumn);
    }

    // equals, hashCode, toString...
}
