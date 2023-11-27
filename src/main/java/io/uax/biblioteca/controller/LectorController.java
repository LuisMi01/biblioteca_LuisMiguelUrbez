package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.LectorDTO;
import io.uax.biblioteca.repos.MultasRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
import io.uax.biblioteca.service.LectorService;
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
@RequestMapping("/lectors")
public class LectorController {

    @GetMapping
    public String mostrarLectores() {
        return "home/IndexLector";
    }
/*
    private final LectorService lectorService;
    private final MultasRepository multasRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public LectorController(final LectorService lectorService,
            final MultasRepository multasRepository, final UsuarioRepository usuarioRepository,
            final PrestamoRepository prestamoRepository) {
        this.lectorService = lectorService;
        this.multasRepository = multasRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("multasValues", multasRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Multas::getId, Multas::getId)));
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getId, Usuario::getNombre)));
        model.addAttribute("lectorPrestamoPrestamoesValues", prestamoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Prestamo::getId, Prestamo::getId)));
    }


    @GetMapping
    public String list(final Model model) {
        model.addAttribute("lectors", lectorService.findAll());
        return "lector/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("lector") final LectorDTO lectorDTO) {
        return "lector/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("lector") @Valid final LectorDTO lectorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lector/add";
        }
        lectorService.create(lectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lector.create.success"));
        return "redirect:/lectors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("lector", lectorService.get(id));
        return "lector/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("lector") @Valid final LectorDTO lectorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lector/edit";
        }
        lectorService.update(id, lectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lector.update.success"));
        return "redirect:/lectors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = lectorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            lectorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("lector.delete.success"));
        }
        return "redirect:/lectors";
    }
*/
}
