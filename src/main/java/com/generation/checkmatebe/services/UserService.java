package com.generation.checkmatebe.services;

import com.generation.checkmatebe.dtos.LoginDTO;
import com.generation.checkmatebe.dtos.RegisterDTO;
import com.generation.checkmatebe.dtos.UserOutputDTO;
import com.generation.checkmatebe.exceptions.InvalidCredentials;
import com.generation.checkmatebe.model.entities.User;
import com.generation.checkmatebe.model.enums.Rank;
import com.generation.checkmatebe.model.enums.Role;
import com.generation.checkmatebe.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{

    @Autowired
    private UserRepository repo;

    public String register(RegisterDTO RegisterDTO) {
        //Controllo della password sul DTO
        String password = RegisterDTO.getPassword();
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new InvalidCredentials("Password not valid");
        }

        //Creazione dell’utente
        User user = new User();
        user.setUsername(RegisterDTO.getUsername());
        user.setEmail(RegisterDTO.getEmail());

        //Hash della password prima di salvarla
        String hash = DigestUtils.md5Hex(RegisterDTO.getPassword());
        user.setPassword(hash);

        //Valori per le colonne NOT NULL
        user.setElo(1000);                   // solo qui
        user.setRank(Rank.fromRating(1000)); // solo qui
        user.setPartiteGiocate(0);
        user.setWinRate(0);
        user.setRole(Role.STANDARD);
        user.setToken(UUID.randomUUID().toString());
        repo.save(user);

        //Salvataggio nel database
        repo.save(user);

        return user.getToken();
    }

    public String login(LoginDTO dto)
    {
        String hash = DigestUtils.md5Hex(dto.getPassword());
        Optional<User> op = repo.findByUsernameAndPassword(dto.getUsername(), hash);

        if(op.isEmpty())
            throw new InvalidCredentials("Invalid username or password");

        return op.get().getToken();
    }

    public User findUserByToken(String token)
    {
        Optional<User> op = repo.findByToken(token);

        if(op.isEmpty())
            throw new InvalidCredentials("Invalid token");

        return op.get();
    }

    public UserOutputDTO readUserDTO(String token)
    {
        User u = findUserByToken(token);
        Rank rank = Rank.fromRating(u.getElo());

        UserOutputDTO dto = new UserOutputDTO();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setRank(rank);
        dto.setElo(u.getElo());
        dto.setPartiteGiocate(u.getPartiteGiocate());
        dto.setWinRate(u.getWinRate());
        dto.setRole(u.getRole());
        return dto;
    }
}