package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.model.MultasDTO;
import io.uax.biblioteca.repos.LectorRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.service.MultasService;
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
@RequestMapping("/multass")
public class MultasController {

    private final MultasService multasService;
    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;

    public MultasController(final MultasService multasService,
            final PrestamoRepository prestamoRepository, final LectorRepository lectorRepository) {
        this.multasService = multasService;
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("prestamoValues", prestamoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Prestamo::getId, Prestamo::getId)));
        model.addAttribute("multasLectorLectorsValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("multases", multasService.findAll());
        return "multas/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("multas") final MultasDTO multasDTO) {
        return "multas/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("multas") @Valid final MultasDTO multasDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "multas/add";
        }
        multasService.create(multasDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("multas.create.success"));
        return "redirect:/multass";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("multas", multasService.get(id));
        return "multas/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("multas") @Valid final MultasDTO multasDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "multas/edit";
        }
        multasService.update(id, multasDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("multas.update.success"));
        return "redirect:/multass";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = multasService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            multasService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("multas.delete.success"));
        }
        return "redirect:/multass";
    }

}
