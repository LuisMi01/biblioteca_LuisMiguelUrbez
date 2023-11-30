package io.uax.biblioteca.controller;

import io.uax.biblioteca.domain.Bibliotecario;
import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.model.*;
import io.uax.biblioteca.repos.BibliotecarioRepository;
import io.uax.biblioteca.repos.LectorRepository;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.service.BibliotecarioService;
import io.uax.biblioteca.service.LectorService;
import io.uax.biblioteca.service.LibroService;
import io.uax.biblioteca.service.PrestamoService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final LibroService libroService;
    private final LectorService lectorService;
    private final BibliotecarioService bibliotecarioService;

    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;
    private final BibliotecarioRepository bibliotecarioRepository;

    public PrestamoController(final PrestamoService prestamoService,
                              LibroService libroService, LectorService lectorService, BibliotecarioService bibliotecarioService, final LibroRepository libroRepository, final LectorRepository lectorRepository,
                              final BibliotecarioRepository bibliotecarioRepository) {
        this.prestamoService = prestamoService;
        this.libroService = libroService;
        this.lectorService = lectorService;
        this.bibliotecarioService = bibliotecarioService;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("estadoPreestamoValues", EstadoPrestamo.values());
        model.addAttribute("libroValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
        model.addAttribute("lectorValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
        model.addAttribute("bibliotecarioValues", bibliotecarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Bibliotecario::getId, Bibliotecario::getNombre)));
        model.addAttribute("prestamoLibroLibroesValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
    }

    @GetMapping
    public String list(final Model model) {

        List<PrestamoDTO> prestamos = prestamoService.findAll();
        model.addAttribute("prestamoes", prestamoService.findAll());

        // Obtener nombres de libros, lectores y bibliotecarios
        List<LibroDTO> libros = libroService.findAll();
        model.addAttribute("libros", libros);
        Map<Integer, String> libroNombres = libros
                .stream()
                .collect(Collectors.toMap(LibroDTO::getId, LibroDTO::getTitulo));

        List<LectorDTO> lectores = lectorService.findAll();
        model.addAttribute("lectores", lectores);
        Map<Integer, String> lectorNombres = lectores
                .stream()
                .collect(Collectors.toMap(LectorDTO::getId, LectorDTO::getNombre));

        List<BibliotecarioDTO> bibliotecarios = bibliotecarioService.findAll();
        model.addAttribute("bibliotecarios", bibliotecarios);
        Map<Integer, String> bibliotecarioNombres = bibliotecarios
                .stream()
                .collect(Collectors.toMap(BibliotecarioDTO::getId, BibliotecarioDTO::getNombre));

        model.addAttribute("libroNombres", libroNombres);
        model.addAttribute("lectorNombres", lectorNombres);
        model.addAttribute("bibliotecarioNombres", bibliotecarioNombres);


        return "prestamo/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("prestamo") final PrestamoDTO prestamoDTO) {
        return "prestamo/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("prestamo") @Valid PrestamoDTO prestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            return "prestamo/add";
        }

        Integer libroSeleccionadoId = prestamoDTO.getLibro();
        LibroDTO libroSeleccionado = libroService.getById(libroSeleccionadoId);

        if (libroSeleccionado != null && libroSeleccionado.getEstado() == EstadoLibro.ROTO) {
            model.addAttribute("error", "No puedes prestar un libro roto.");
            return "prestamo/add";
        }


        // Verificar la disponibilidad de libros antes de realizar el préstamo
        List<LibroDTO> librosDisponibles = libroService.getLibrosDisponibles();
        if (librosDisponibles.isEmpty()) {
            model.addAttribute("error_disponibilidad", "No hay libros disponibles en este momento.");
            return "prestamo/add";
        }

        // Agregar los libros disponibles al modelo
        model.addAttribute("libros", librosDisponibles);
        model.addAttribute("prestamo", prestamoDTO);



        prestamoService.create(prestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.create.success"));
        return "redirect:/prestamos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("prestamo", prestamoService.get(id));
        PrestamoDTO prestamoDTO = prestamoService.get(id);
        List<LibroDTO> libro = libroService.findAll();
        model.addAttribute("prestamo", prestamoDTO);
        model.addAttribute("libro", libro);

        return "prestamo/edit";
    }



   @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("prestamo") @Valid PrestamoDTO prestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            return "prestamo/edit";
        }

        Integer libroSeleccionadoId = prestamoDTO.getLibro();
        LibroDTO libroSeleccionado = libroService.getById(libroSeleccionadoId);

        if (libroSeleccionado != null && libroSeleccionado.getEstado() == EstadoLibro.ROTO) {
            model.addAttribute("error", "No puedes prestar un libro roto.");
            return "prestamo/edit";
        }


       // Obtener el préstamo existente u inicializar uno nuevo
        prestamoDTO = (id != null) ? prestamoService.getById(id) : new PrestamoDTO();

        // Verificar la disponibilidad de libros antes de mostrar la vista
        List<LibroDTO> librosDisponibles = libroService.getLibrosDisponibles();
       if (librosDisponibles.isEmpty()) {
           model.addAttribute("error_disponibilidad", "No hay libros disponibles en este momento.");
           return "redirect:/prestamo/edit/{id}?error=disponibilidad";
       }

        // Agregar los libros disponibles al modelo
        model.addAttribute("libros", librosDisponibles);
        model.addAttribute("prestamo", prestamoDTO);

       prestamoService.update(id, prestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.update.success"));
        return "redirect:/prestamos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
                         final RedirectAttributes redirectAttributes) {

        // Obtener el préstamo a eliminar antes de borrarlo
        PrestamoDTO prestamoDTO = prestamoService.getById(id);

        // Verificar que el préstamo existe
        if (prestamoDTO == null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("prestamo.notfound"));
            return "redirect:/prestamos";
        }

        // Obtener el libro asociado al préstamo
        LibroDTO libroPrestado = libroService.getById(prestamoDTO.getLibro());

        // Incrementar la cantidad disponible del libro
        if (libroPrestado != null) {
            libroPrestado.setEjemplaresDisponibles(libroPrestado.getEjemplaresDisponibles() + 1);
            libroService.update(libroPrestado.getId(), libroPrestado);
        }

        // Eliminar el préstamo después de obtener la información necesaria
        prestamoService.delete(id);

        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.delete.success"));
        return "redirect:/prestamos";
    }



}
