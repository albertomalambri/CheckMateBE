package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.LoginDTO;
import com.generation.checkmatebe.dtos.RegisterDTO;
import com.generation.checkmatebe.dtos.UserOutputDTO;
import com.generation.checkmatebe.exceptions.InvalidCredentials;
import com.generation.checkmatebe.model.entities.User;
import com.generation.checkmatebe.model.enums.Rank;
import com.generation.checkmatebe.model.repositories.UserRepository;
import com.generation.checkmatebe.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository repo;

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO dto, HttpServletResponse response)
    {
        String tokenUtente = userService.register(dto);

        Cookie cookie = new Cookie("token", tokenUtente);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    public UserOutputDTO login(@RequestBody LoginDTO dto, HttpServletResponse response)
    {
        //hash della password ricevuta
        String hashedPassword = DigestUtils.md5Hex(dto.getPassword());

        //ciclo sugli utenti registrati
        List<User> users = repo.findAll();
        User foundUser = null;
        for (User u : users) {
            if (u.getUsername().equals(dto.getUsername()) && u.getPassword().equals(hashedPassword)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser == null) {
            throw new InvalidCredentials("Invalid username or password");
        }

        //genera cookie con token
        Cookie cookie = new Cookie("token", foundUser.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        //ritorna info utente al frontend
        UserOutputDTO dtoOut = new UserOutputDTO();
        dtoOut.setUsername(foundUser.getUsername());
        dtoOut.setEmail(foundUser.getEmail());
        dtoOut.setRole(foundUser.getRole());
        dtoOut.setRank(foundUser.getRank());
        dtoOut.setElo(foundUser.getElo());
        dtoOut.setPartiteGiocate(foundUser.getPartiteGiocate());
        dtoOut.setWinRate(foundUser.getWinRate());

        return dtoOut;
    }


    @GetMapping("/userinformation")
    public UserOutputDTO getUserInfo(HttpServletRequest request)
    {
        Optional<User> users = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equalsIgnoreCase("token")).map(token -> userService.findUserByToken(token.getValue())).findFirst();
        if (users.isEmpty())
            return null;
        return userService.readUserDTO(users.get().getToken());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String gestisciTutto(Exception e)
    {
        return "Operazione fallita, ulteriori dettagli "+e.getMessage();
    }


    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // elimina subito
        response.addCookie(cookie);
    }
}