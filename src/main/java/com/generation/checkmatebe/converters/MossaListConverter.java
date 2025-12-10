package com.generation.checkmatebe.converters;


import com.generation.checkmatebe.model.entities.Mossa;
import jakarta.persistence.Converter;
import java.util.LinkedList;

@Converter(autoApply = false)
public class MossaListConverter extends JsonConverter<LinkedList<Mossa>> {
    public MossaListConverter() {
        super((Class<LinkedList<Mossa>>)(Class<?>)LinkedList.class);
    }
}