package io.uax.biblioteca.controller;


import io.uax.biblioteca.model.BibliotecarioDTO;
import io.uax.biblioteca.service.AdminService;
import io.uax.biblioteca.service.BibliotecarioService;
import io.uax.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admins")
public class AdministradorController {
    public AdministradorController(AdminService adminService, LibroService libroService, BibliotecarioService bibliotecarioService) {
        this.adminService = adminService;
        this.libroService = libroService;

        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    public String mostrarAdministradores() {
        return "home/IndexAdmin";
    }

    private final AdminService adminService;
    private final LibroService libroService;

    private final BibliotecarioService bibliotecarioService;


   /* @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("multasValues", multasRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Multas::getId, Multas::getId)));
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getId, Usuario::getNombre)));
        model.addAttribute("prestamoValues", prestamoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Prestamo::getId, Prestamo::getId)));
        model.addAttribute("libroValues", libroService.findAll()
                .stream()
                .collect(CustomCollectors.toSortedMap(LibroDTO::getId, LibroDTO::getTitulo)));
    }*/

    @GetMapping("/librosAdministrador")
    public String mostrarLibrosAdministrador(final Model model) {
        model.addAttribute("libroes", libroService.findAll());
        return "administrador/listLibrosAdmin";
    }

    @GetMapping("/listarBibliotecarios")
    public String list(final Model model) {
        model.addAttribute("bibliotecarios", bibliotecarioService.findAll());
        return "bibliotecario/list";
    }


    @GetMapping("/contratarBibliotecarios")
    public String addBibliotecario(@ModelAttribute("bibliotecario") final BibliotecarioDTO bibliotecarioDTO) {
        return "bibliotecario/add";
    }



}
