package com.generation.checkmatebe.dtos;

import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CasellaDTO
{
    private int row;
    private int column;
    private Color colorePezzo;
    private Pezzo pezzo;
    private String nomeCasella;
}

