package io.uax.biblioteca.administrador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministradorController {
    //controller para el administrador

    @GetMapping("/admin")
    public String mostrarBibliotecarios() {
        return "home/IndexAdmin";
    }



}
