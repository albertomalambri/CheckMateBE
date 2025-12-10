package com.generation.checkmatebe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.checkmatebe.converters.FenConverter;
import com.generation.checkmatebe.dtos.CasellaDTO;
import com.generation.checkmatebe.dtos.MossaDTO;
import com.generation.checkmatebe.dtos.PartitaDTO;
import com.generation.checkmatebe.dtos.ScacchieraGamestateDTO;
import com.generation.checkmatebe.model.entities.*;
import com.generation.checkmatebe.model.enums.Color;
import com.generation.checkmatebe.model.enums.Pezzo;
import com.generation.checkmatebe.model.repositories.PartitaRepo;
import com.generation.checkmatebe.model.repositories.ScacchieraRepository;
import com.generation.checkmatebe.model.repositories.UserRepository;
import com.google.genai.Client;
import com.google.genai.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameStateService
{
    @Autowired
    private ScacchieraRepository scacchieraRepository;

    @Autowired
    private PartitaRepo pRepo;

    @Autowired
    private UserRepository uRepo;

    public List<CasellaDTO> findAllAsDto(ScacchieraGamestate gameState) {
//        ScacchieraGamestate gameState = repo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("gamestate not found"));
        Casella[][] scacchiera = gameState.getScacchiera();
        List<CasellaDTO> caselleDTO = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++) {
                Casella casella = scacchiera[i][j];
                CasellaDTO dto = new CasellaDTO();
                dto.setRow(casella.getRow());
                dto.setColumn(casella.getColumn());
                dto.setNomeCasella(casella.getNomeCasella());
                dto.setPezzo(casella.getPezzo());
                dto.setColorePezzo(casella.getColorePezzo());
                caselleDTO.add(dto);
            }
        }
        return caselleDTO;
    }
    public ScacchieraGamestateDTO inizializzaGamestate(Long id)
    {
        ScacchieraGamestate gameState = new ScacchieraGamestate();
        Casella[][] scacchiera = new Casella[8][8];
        LinkedList<Mossa> previousMoves = new LinkedList<>();
        for (int i = 0; i < 8; i++) { //righe
            for (int j = 0; j < 8; j++) { //colonne
                Casella casella = new Casella(i, j);
                setupCasella(casella);
                scacchiera[i][j] = casella;
            }
        }
        gameState.setScacchiera(scacchiera);
        gameState.setPreviousMoves(previousMoves);
        gameState.setChessboard(pRepo.getReferenceById(id));
        Partita p = pRepo.getReferenceById(id);
        p.setGamestate(gameState);
        scacchieraRepository.save(gameState);
        pRepo.save(p);
        return convertToDtoScacchiera(gameState);
    }
    private void setupCasella(Casella casella)
    {
        int r = casella.getRow();
        int c = casella.getColumn();

        if (r == 1) {
            casella.setPezzo(Pezzo.PEDONE);
            casella.setColorePezzo(Color.NERO);
        } else if (r == 6) {
            casella.setPezzo(Pezzo.PEDONE);
            casella.setColorePezzo(Color.BIANCO);
        } else if (r == 0 || r == 7) {
            Color colore = (r == 0) ? Color.NERO : Color.BIANCO;
            Pezzo pezzo = switch (c) {
                case 0, 7 -> Pezzo.TORRE;
                case 1, 6 -> Pezzo.CAVALLO;
                case 2, 5 -> Pezzo.ALFIERE;
                case 3 -> Pezzo.REGINA;
                case 4 -> Pezzo.RE;
                default -> null;
            };
            casella.setPezzo(pezzo);
            casella.setColorePezzo(colore);
        } else
        {
            casella.svuotaCasella(); // pezzo = null
            casella.setColorePezzo(null);
        }
    }

    public ScacchieraGamestateDTO convertToDtoScacchiera(ScacchieraGamestate gamestate) {
        ScacchieraGamestateDTO dto = new ScacchieraGamestateDTO();
        dto.setId(gamestate.getId());
        dto.setScacchiera(findAllAsDto(gamestate));
        dto.setCurrentPlayer(gamestate.getCurrentPlayer());
        dto.setCheck(gamestate.isCheck());
        dto.setCheckMate(gamestate.isCheckMate());
        dto.setStallo(gamestate.isStallo());
        return dto;
    }

    public PartitaDTO fineGamestate(User user, Long id) {
        Optional<ScacchieraGamestate> gamestate = scacchieraRepository.findById(id);
        Partita game;
        if (gamestate.isPresent()) {
            game = user.getPartite().stream().filter(partita -> partita == pRepo.findPartitaById(gamestate.get().getChessboard().getId())).toList().get(0);
            game.setMosse(gamestate.get().getPreviousMoves());
            if (gamestate.get().isCheckMate()) {
                if (gamestate.get().getCurrentPlayer() == Color.NERO)
                    game.setRisultato("BIANCO");
                else
                    game.setRisultato("NERO");
            }
            user.getPartite().add(game);
            user.setPartiteGiocate(user.getPartiteGiocate()+1);
            pRepo.save(game);
            uRepo.save(user);
            return convertPartitaToDto(game);
        }
        else
            return null;


    }

    public PartitaDTO convertPartitaToDto(Partita game) {
        PartitaDTO dto = new PartitaDTO();
        dto.setId(game.getId());
        dto.setGiocatoreBianco(game.getGiocatoreBianco());
        dto.setGiocatoreNero(game.getGiocatoreNero());
        dto.setRisultato(game.getRisultato());
        game.setGamestate(scacchieraRepository.findByChessboard_Id(game.getId()).get());
        dto.setMosse(convertiMosseDto(game.getGamestate().getPreviousMoves()));
//        dto.setStatoFinaleFEN();
        return dto;
    }

    private List<MossaDTO> convertiMosseDto(LinkedList<Mossa> previousMoves) {
        List<MossaDTO> lista = new ArrayList<>();
        for (Mossa m : previousMoves) {
            MossaDTO dto = new MossaDTO();
            dto.setNumero(m.getTurno());
            dto.setDa(m.getStart().getNomeCasella());
            dto.setA(m.getEnd().getNomeCasella());
            dto.setPezzo(m.getPezzo().getCodice());
            lista.add(dto);
        }
        return lista;
    }

    public MossaDTO mossaAI(Long id) {
        Casella[][] scacchiera = scacchieraRepository.findById(id).get().getScacchiera();
        FenConverter converter= new FenConverter();
        String scacchieraFEN = converter.boardToFen(scacchiera,Color.BIANCO);

        // 1. Inizializza il Client con la tua API Key
        Client client = Client.builder()
                .apiKey("AIzaSyDQzXL74S0AcDK8R-fiRYsMulHIZyFuKFM")
                .build();


        // 2. Definisci lo Schema JSON corrispondente alla tua MossaDTO
        String mossaJsonSchema = """
            {
              "type": "object",
              "properties": {
                "numero": { "type": "integer" },
                "da": { "type": "string" },
                "a": { "type": "string" },
                "pezzo": {
                  "type": "string",
                  "enum": ["AL", "PE", "RG", "RE", "TO", "CA"]
                },
                "cattura": { "type": "boolean" },
                "arrocco": { "type": "boolean" },
                "promozione": { "type": "boolean" }
              },
              "required": ["numero", "da", "a", "pezzo", "cattura", "arrocco", "promozione"]
            }
            """;

        Schema mossaSchema = Schema.fromJson(mossaJsonSchema);


        // 3. Istruzione di sistema
        Content systemInstruction = Content.fromParts(
                Part.fromText("Sei un motore di scacchi. La tua unica risposta deve essere un oggetto JSON valido che descrive la mossa del nero, nel formato DTO specificato e il FEN è: " + scacchieraFEN )
        );

        // 4. Configurazione della richiesta
        GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(systemInstruction)
                .responseMimeType("application/json")
                .responseSchema(mossaSchema)
                .candidateCount(1)
                .build();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.5-flash", "prossima mossa? in meno di 2 secondi", config);


        // 7. Stampa la risposta JSON
        System.out.println("Risposta JSON: " + response.text());
        ObjectMapper mapper = new ObjectMapper();
        try {
            MossaDTO mossaDTO = mapper.readValue(response.text(), MossaDTO.class);

            return mossaDTO;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    private int numero;
//    private String da; // "e2"
//    private String a;  // "e4"
//    private String pezzo; // "pedone", "cavallo", ecc.
//    private boolean cattura;
//    private boolean arrocco;
//    private boolean promozione;

}
