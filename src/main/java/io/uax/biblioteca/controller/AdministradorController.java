package io.uax.biblioteca.controller;


import io.uax.biblioteca.repos.MultasRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
import io.uax.biblioteca.service.AdminService;
import io.uax.biblioteca.service.LibroService;
import io.uax.biblioteca.util.CustomCollectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admins")
public class AdministradorController {
    public AdministradorController(AdminService adminService, LibroService libroService, MultasRepository multasRepository, UsuarioRepository usuarioRepository, PrestamoRepository prestamoRepository) {
        this.adminService = adminService;
        this.libroService = libroService;
        this.multasRepository = multasRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping
    public String mostrarAdministradores() {
        return "home/IndexAdmin";
    }

    private final AdminService adminService;
    private final LibroService libroService;
    private final MultasRepository multasRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;


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



}
