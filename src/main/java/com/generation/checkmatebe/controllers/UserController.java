package com.generation.checkmatebe.controllers;

import com.generation.checkmatebe.dtos.LoginDTO;
import com.generation.checkmatebe.dtos.RegisterDTO;
import com.generation.checkmatebe.dtos.UserOutputDTO;
import com.generation.checkmatebe.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController 
{
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public void register(@RequestBody RegisterDTO dto, HttpServletResponse response)
    {
        String tokenUtente = userService.register(dto);

        Cookie cookie = new Cookie("token", tokenUtente);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    @PostMapping("login")
    public void login(@RequestBody LoginDTO dto, HttpServletResponse response)
    {
        String tokenUtente = userService.login(dto);

        Cookie cookie = new Cookie("token", tokenUtente);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    @GetMapping("/userinformation")
    public UserOutputDTO getUserInfo(@CookieValue(required = false) String token)
    {
        if(token == null)
            return null;
        return userService.readUserDTO(token);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String gestisciTutto(Exception e)
    {
        return "Operazione fallita, ulteriori dettagli "+e.getMessage();
    }
}
