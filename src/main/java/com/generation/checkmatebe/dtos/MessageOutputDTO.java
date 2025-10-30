package com.generation.checkmatebe.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageOutputDTO
{
    private Long id;
    private String usernameSender;
    private boolean archiviato;
    private LocalDateTime timeStamp;
    private String title;
    private String content;
}
