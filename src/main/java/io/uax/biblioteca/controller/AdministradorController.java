package io.uax.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class AdministradorController {
    @GetMapping
    public String mostrarAdministradores() {
        return "home/IndexAdmin";
    }



}
