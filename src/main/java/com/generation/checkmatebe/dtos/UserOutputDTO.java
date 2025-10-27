package com.generation.checkmatebe.dtos;

import com.generation.checkmatebe.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutputDTO
{
    private String username;
    private Role role;
    private String email;
}
