package com.generation.checkmatebe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.google.genai.types.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.genai.Client;

import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
// Use Builder class for instantiation. Explicitly set the API key to use Gemini
// Developer backend.


@SpringBootApplication
public class CheckMateBeApplication
{public static void main(String[] args) {
//    // 1. Inizializza il Client con la tua API Key
//    Client client = Client.builder()
//            .apiKey("AIzaSyDQzXL74S0AcDK8R-fiRYsMulHIZyFuKFM")
//            .build();
//
//
//    // 2. Definisci lo Schema JSON corrispondente alla tua MossaDTO
//    String mossaJsonSchema = """
//            {
//              "type": "object",
//              "properties": {
//                "numero": { "type": "integer" },
//                "da": { "type": "string" },
//                "a": { "type": "string" },
//                "pezzo": {
//                  "type": "string",
//                  "enum": ["AL", "PE", "RG", "RE", "TO", "CA"]
//                },
//                "cattura": { "type": "boolean" },
//                "arrocco": { "type": "boolean" },
//                "promozione": { "type": "boolean" }
//              },
//              "required": ["numero", "da", "a", "pezzo", "cattura", "arrocco", "promozione"]
//            }
//            """;
//
//    Schema mossaSchema = Schema.fromJson(mossaJsonSchema);
//
//
//    // 3. Istruzione di sistema
//    Content systemInstruction = Content.fromParts(
//            Part.fromText("Sei un motore di scacchi. La tua unica risposta deve essere un oggetto JSON valido " +
//                    "che descrive la mossa del nero, nel formato DTO specificato " +
//                    "e devi mettere prima la riga e poi la colonna in minuscolo.")
//    );
//
//    // 4. Configurazione della richiesta
//    GenerateContentConfig config = GenerateContentConfig.builder()
//            .systemInstruction(systemInstruction)
//            .responseMimeType("application/json")
//            .responseSchema(mossaSchema)
//            .candidateCount(1)
//            .build();
//
//    GenerateContentResponse response =
//            client.models.generateContent("gemini-2.5-flash", "devi fare la prima mossa", config);
//
//
//    // 7. Stampa la risposta JSON
//    System.out.println("Risposta JSON: " + response.text());
//    ObjectMapper mapper = new ObjectMapper();
//    try {
//        MossaDTO mossaDTO = mapper.readValue(response.text(), MossaDTO.class);
//        System.out.println(mossaDTO.toString());
//    } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//    }
//
//    // 8. Avvia Spring Boot
   SpringApplication.run(CheckMateBeApplication.class, args);
}
}

