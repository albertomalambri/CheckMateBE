package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.MessageInputDTO;
import com.generation.checkmatebe.dtos.MessageOutputDTO;
import com.generation.checkmatebe.model.entities.IntercomMessage;
import com.generation.checkmatebe.model.repositories.MessageRepo;
import com.generation.checkmatebe.model.repositories.UserRepo;
import com.generation.checkmatebe.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/messages")
public class MessageController
{
    @Autowired
    MessageService serv;

    @Autowired
    MessageRepo mRepo;
    @Autowired
    UserRepo uRepo;


    @PostMapping
    public void MessageOutputDTO(@RequestBody MessageInputDTO dto)
    {
        serv.convertToMessageAndSave(dto);
    }

    @GetMapping("/{username}")
    public List<MessageOutputDTO> mieiMessaggi(@PathVariable String username)
    {
        return serv.leggiMieiMessaggi(username);
    }

}
