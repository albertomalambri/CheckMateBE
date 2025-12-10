package com.generation.checkmatebe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.checkmatebe.model.entities.Casella;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter
public class ConverterCaselle implements AttributeConverter<Casella[][], String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Casella[][] scacchiera) {
        try {
            return mapper.writeValueAsString(scacchiera);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Errore nella serializzazione della scacchiera", e);
        }
    }

    @Override
    public Casella[][] convertToEntityAttribute(String json) {
        try {
            return mapper.readValue(json, Casella[][].class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Errore nella deserializzazione della scacchiera", e);
        }
    }
}