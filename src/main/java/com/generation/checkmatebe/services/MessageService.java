package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.MessageInputDTO;
import com.generation.checkmatebe.dtos.MessageOutputDTO;
import com.generation.checkmatebe.model.entities.IntercomMessage;
import com.generation.checkmatebe.model.entities.User;
import com.generation.checkmatebe.model.repositories.MessageRepo;
import com.generation.checkmatebe.model.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService
{
    @Autowired
    MessageRepo mRepo;
    @Autowired
    UserRepo uRepo;

    public void convertToMessageAndSave(MessageInputDTO dto)
    {
        IntercomMessage m = new IntercomMessage();

        User destinatario = uRepo.findByUsername((dto.getUsernameReceiver()));
        User mittente = uRepo.findByUsername(dto.getUsernameSender());

        m.setDestinatario(destinatario);
        m.setMittente(mittente);
        m.setContent(dto.getContent());
        m.setTitolo(dto.getTitle());
        m.setTimestamp(LocalDateTime.now());
        mRepo.save(m);

    }

    public List<MessageOutputDTO> leggiMieiMessaggi(String username)
    {
        User u = uRepo.findByUsername(username);
        return u.getMessaggiRicevuti().stream().map(m -> convertToDto(m)).toList();
    }
        private MessageOutputDTO convertToDto(IntercomMessage m)
        {
            MessageOutputDTO dto = new MessageOutputDTO();
            dto.setId(m.getId());
            dto.setUsernameSender(m.getMittente().getUsername());
            dto.setArchiviato(m.isArchiviato());
            dto.setContent(m.getContent());
            dto.setTitle(m.getTitolo());
            dto.setTimeStamp(m.getTimestamp());
            return dto;

        }
    }
