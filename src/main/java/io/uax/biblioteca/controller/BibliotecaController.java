package io.uax.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BibliotecaController {
    @GetMapping("/biblioteca")
    public String mostrarBiblioteca() {
        return "home/IndexBiblioteca";
    }
}
