package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.enums.Rank;
import com.generation.checkmatebe.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //autenticazione utente
    //da implementare: email automatiche/di registrazione o recupero password, sponsorizzazione tornei o eventi
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email required")
    private String email;

    private String password;

    @Column(nullable = false)
    private String username;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_rank", nullable = false)
    private Rank rank;

    @Column(nullable = false)
    private int elo;

    private int partiteGiocate;
    private double winRate;

    private Role role = Role.USER;
    private String token;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "destinatario")
    private Set<IntercomMessage> messaggiRicevuti;

    public User() {
    }
}