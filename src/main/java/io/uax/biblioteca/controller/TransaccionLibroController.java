package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.model.EstadoLibro;
import io.uax.biblioteca.model.TransaccionLibroDTO;
import io.uax.biblioteca.repos.TransaccionLibroRepository;
import io.uax.biblioteca.service.LibroService;
import io.uax.biblioteca.service.TransaccionLibroService;
import io.uax.biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.antlr.v4.runtime.misc.MurmurHash.update;


@Controller
@RequestMapping("/transaccionLibros")
public class TransaccionLibroController {

    private final TransaccionLibroService transaccionLibroService;

    private final TransaccionLibroRepository transaccionLibroRepository;
    private final LibroService libroService;

    public TransaccionLibroController(final TransaccionLibroService transaccionLibroService, TransaccionLibroRepository transaccionLibroRepository, LibroService libroService) {
        this.transaccionLibroService = transaccionLibroService;
        this.transaccionLibroRepository = transaccionLibroRepository;
        this.libroService = libroService;
    }

    @GetMapping("/librosRotos")
    public String getLibrosRotos(Model model) {
        List<Libro> librosRotos = transaccionLibroService.getLibrosRotos();
        model.addAttribute("librosRotos", librosRotos);
        return "transaccionLibro/librosRotos";
    }

    @PostMapping("/librosRotos")
    public String postLibrosRotos(Model model) {
        List<Libro> librosRotos = transaccionLibroService.getLibrosRotos();
        //cambiar el estado del libro
        for (Libro libro : librosRotos) {
            libroService.updateEstado(libro.getId(), EstadoLibro.NUEVO);
        }
        model.addAttribute("librosRotos", librosRotos);
        return "transaccionLibro/librosRotos";
    }

    @GetMapping("/arreglarLibros")
    public String arreglarLibros(Model model) {
        List<Libro> librosRotos = transaccionLibroService.getLibrosRotos();
        //cambiar el estado del libro
        for (Libro libro : librosRotos) {
            libroService.updateEstado(libro.getId(), EstadoLibro.NUEVO);
        }
        model.addAttribute("librosRotos", librosRotos);
        return "transaccionLibro/librosRotos";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("transaccionLibro") final TransaccionLibroDTO transaccionLibroDTO) {
        return "transaccionLibro/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("transaccionLibro") @Valid final TransaccionLibroDTO transaccionLibroDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaccionLibro/add";
        }
        transaccionLibroService.create(transaccionLibroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaccionLibro.create.success"));
        return "redirect:/transaccionLibros";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("transaccionLibro", transaccionLibroService.get(id));
        return "transaccionLibro/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("transaccionLibro") @Valid final TransaccionLibroDTO transaccionLibroDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaccionLibro/edit";
        }
        transaccionLibroService.update(id, transaccionLibroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaccionLibro.update.success"));
        return "redirect:/transaccionLibros";
    }

    public void delete(Integer id) {
        if (transaccionLibroRepository.existsById(id)) {
            transaccionLibroRepository.updateEstado(id, EstadoLibro.NUEVO);
        }
    }


}
