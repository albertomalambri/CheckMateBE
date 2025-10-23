package com.generation.checkmatebe.model.converters;

import com.generation.checkmatebe.model.entities.Casella;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CasellaMatrixConverter extends JsonConverter<Casella[][]> {
    public CasellaMatrixConverter() {
        super(Casella[][].class);
    }
}
