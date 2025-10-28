package com.generation.checkmatebe.model.entities;

import com.generation.checkmatebe.model.enums.Rank;
import com.generation.checkmatebe.model.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

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

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "The password must contain at least 8 characters, one uppercase, one lowercase, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "Username required")
    private String username;
    @NotBlank
    private Rank rank;
    //fase di ranking elo=null
    private int elo;
    @PositiveOrZero
    private int partiteGiocate;
    @PositiveOrZero
    private double winRate;

    private Role role;

    private String token;

}
