package org.esfe.controladores;


import org.esfe.modelos.Administrador;
import org.esfe.modelos.Barbero;
import org.esfe.servicios.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IAdministradorService administradorService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("nombre") Optional<String> nombre) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        String nombreSearch = nombre.orElse("");
        Page<Administrador> administradores = administradorService.buscarPorNombre(nombreSearch, pageable);
        model.addAttribute("administradores", administradores);
        // Para mantener los valores de búsqueda en el formulario HTML cuando la página se recarga
        model.addAttribute("nombreSearch", nombreSearch);

        int totalPages = administradores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);


        }
        return "administrador/index";
    }

    @GetMapping("/crear")
    public String create(Administrador administrador) {
        return "Administrador/crear";
    }

    @PostMapping("/crear")
    public String save(Administrador administrador, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute(administrador);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "administrador/crear";
        }

        administradorService.crearOEditar(administrador);
        attributes.addFlashAttribute("msg", "Administrador creado correctamente");
        return "redirect:/administrador";
    }

}
