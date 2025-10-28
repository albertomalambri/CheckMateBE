package com.generation.checkmatebe.dtos;

import com.generation.checkmatebe.model.enums.Rank;
import com.generation.checkmatebe.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserOutputDTO
{
    private long id;
    private String username;
    private String email;
    private Rank rank;
    private int elo;
    private int partiteGiocate;
    private double winRate;
    private Role role;

    public UserOutputDTO() {

    }
}