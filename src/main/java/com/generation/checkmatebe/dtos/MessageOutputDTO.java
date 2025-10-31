package com.generation.checkmatebe.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
