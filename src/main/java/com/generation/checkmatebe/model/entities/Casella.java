package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.enums.Color;
import com.generation.checkmatebe.model.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Casella {


    private Position position;
    @Enumerated(EnumType.STRING)
    private Color color;

}



