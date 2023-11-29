package io.uax.biblioteca.controller;


import io.uax.biblioteca.model.BibliotecarioDTO;
import io.uax.biblioteca.service.AdminService;
import io.uax.biblioteca.service.BibliotecarioService;
import io.uax.biblioteca.service.LibroService;
import io.uax.biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    //Listar los libros que hay disponibles en la biblioteca
    @GetMapping("/librosAdministrador")
    public String mostrarLibrosAdministrador(final Model model) {
        model.addAttribute("libroes", libroService.findAll());
        return "administrador/listLibrosAdmin";
    }

    //listar los bibliotecarios que hay en la biblioteca
    @GetMapping("/listarBibliotecarios")
    public String list(final Model model) {
        model.addAttribute("bibliotecarios", bibliotecarioService.findAll());
        return "bibliotecario/list";
    }

    //Contratar nuevos bibliotecarios
    @GetMapping("/listarBibliotecarios/contratarBibliotecarios")
    public String add(@ModelAttribute("bibliotecario") final BibliotecarioDTO bibliotecarioDTO) {
        return "bibliotecario/contratarBibliotecarios";
    }

    @PostMapping("/listarBibliotecarios/contratarBibliotecarios")
    public String add(
            @ModelAttribute("bibliotecario") @Valid final BibliotecarioDTO bibliotecarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bibliotecario/contratarBibliotecarios";
        }
        bibliotecarioService.create(bibliotecarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecario.create.success"));
        return "redirect:/admins/listarBibliotecarios";
    }


}
