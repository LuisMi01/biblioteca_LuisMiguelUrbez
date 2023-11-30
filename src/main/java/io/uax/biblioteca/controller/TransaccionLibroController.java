package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.model.TransaccionLibroDTO;
import io.uax.biblioteca.service.LibroService;
import io.uax.biblioteca.service.TransaccionLibroService;
import io.uax.biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/transaccionLibros")
public class TransaccionLibroController {

    private final TransaccionLibroService transaccionLibroService;
    private final LibroService libroService;

    public TransaccionLibroController(final TransaccionLibroService transaccionLibroService, LibroService libroService) {
        this.transaccionLibroService = transaccionLibroService;
        this.libroService = libroService;
    }

    @GetMapping("/librosRotos")
    public String getLibrosRotos(Model model) {
        List<Libro> librosRotos = transaccionLibroService.getLibrosRotos();
        model.addAttribute("librosRotos", librosRotos);
        return "transaccionLibro/librosRotos";
    }


    @PostMapping("/arreglarLibros")
    public String arreglarLibros() {
        transaccionLibroService.arreglarLibro();
        return "redirect:/librosRotos";
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

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        transaccionLibroService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("transaccionLibro.delete.success"));
        return "redirect:/transaccionLibros";
    }

}
