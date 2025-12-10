package com.generation.checkmatebe.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageInputDTO
{
    private String usernameSender;
    private String usernameReceiver;
    private String title;
    private String content;
}
