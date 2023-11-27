package io.uax.biblioteca.controller;

import io.uax.biblioteca.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.PoliticaPrestamoDTO;
import io.uax.biblioteca.repos.BibliotecaRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
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
@RequestMapping("/politicaPrestamos")
public class PoliticaPrestamoController {

    private final PoliticaPrestamoService politicaPrestamoService;
    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public PoliticaPrestamoController(final PoliticaPrestamoService politicaPrestamoService,
            final BibliotecaRepository bibliotecaRepository,
            final UsuarioRepository usuarioRepository) {
        this.politicaPrestamoService = politicaPrestamoService;
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("bibliotecaValues", bibliotecaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Biblioteca::getId, Biblioteca::getId)));
        model.addAttribute("politicaPrestamoUsuarioUsuariosValues", usuarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getId, Usuario::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("politicaPrestamoes", politicaPrestamoService.findAll());
        return "politicaPrestamo/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("politicaPrestamo") final PoliticaPrestamoDTO politicaPrestamoDTO) {
        return "politicaPrestamo/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("politicaPrestamo") @Valid final PoliticaPrestamoDTO politicaPrestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "politicaPrestamo/add";
        }
        politicaPrestamoService.create(politicaPrestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("politicaPrestamo.create.success"));
        return "redirect:/politicaPrestamos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("politicaPrestamo", politicaPrestamoService.get(id));
        return "politicaPrestamo/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("politicaPrestamo") @Valid final PoliticaPrestamoDTO politicaPrestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "politicaPrestamo/edit";
        }
        politicaPrestamoService.update(id, politicaPrestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("politicaPrestamo.update.success"));
        return "redirect:/politicaPrestamos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        politicaPrestamoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("politicaPrestamo.delete.success"));
        return "redirect:/politicaPrestamos";
    }

}
