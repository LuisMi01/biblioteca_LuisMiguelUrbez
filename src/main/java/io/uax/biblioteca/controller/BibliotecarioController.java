package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.BibliotecarioDTO;
import io.uax.biblioteca.repos.BibliotecaRepository;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
import io.uax.biblioteca.service.BibliotecarioService;
import io.uax.biblioteca.service.PoliticaPrestamoService;
import io.uax.biblioteca.util.CustomCollectors;
import io.uax.biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    @GetMapping
    public String mostrarBibliotecarios() {
        return "home/IndexBibliotecario";
    }

    @GetMapping("/politicaPrestamos")
    public String listPoliticas(final Model model) {
        model.addAttribute("politicaPrestamoes", politicasService.findAll());
        return "politicaPrestamo/listPoliticasBibliotecario";
    }

    private final BibliotecarioService bibliotecarioService;
    private final UsuarioRepository usuarioRepository;
    private final BibliotecaRepository bibliotecaRepository;
    private final LibroRepository libroRepository;

    private final PoliticaPrestamoService politicasService;
    public BibliotecarioController(final BibliotecarioService bibliotecarioService,
                                   final UsuarioRepository usuarioRepository,
                                   final BibliotecaRepository bibliotecaRepository,
                                   final LibroRepository libroRepository, PoliticaPrestamoService politicasService) {
        this.bibliotecarioService = bibliotecarioService;
        this.usuarioRepository = usuarioRepository;
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
        this.politicasService = politicasService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getId, Usuario::getNombre)));
        model.addAttribute("bibliotecarioBibliotecaBibliotecasValues", bibliotecaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Biblioteca::getId, Biblioteca::getId)));
        model.addAttribute("bibliotecarioLibroLibroesValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
    }

    @GetMapping("/list")
    public String list(final Model model) {
        model.addAttribute("bibliotecarios", bibliotecarioService.findAll());
        return "bibliotecario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bibliotecario") final BibliotecarioDTO bibliotecarioDTO) {
        return "bibliotecario/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("bibliotecario") @Valid final BibliotecarioDTO bibliotecarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bibliotecario/add";
        }
        bibliotecarioService.create(bibliotecarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecario.create.success"));
        return "redirect:/bibliotecarios";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("bibliotecario", bibliotecarioService.get(id));
        return "bibliotecario/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("bibliotecario") @Valid final BibliotecarioDTO bibliotecarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bibliotecario/edit";
        }
        bibliotecarioService.update(id, bibliotecarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecario.update.success"));
        return "redirect:/bibliotecarios";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = bibliotecarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            bibliotecarioService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bibliotecario.delete.success"));
        }
        return "redirect:/bibliotecarios";
    }



}
