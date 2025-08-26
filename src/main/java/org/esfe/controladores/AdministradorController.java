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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/detalle/{id}")
    public String verDetalleAdministrador(@PathVariable Long id, Model model) {
        // 1. Busca el administrador por su ID usando el servicio
        // que devuelve un Optional<Administrador>.
        Optional<Administrador> administradorOptional = administradorService.buscarPorId(id.intValue()); // Convertir Long a Integer si tu servicio lo espera

        if (administradorOptional.isPresent()) {
            // 2. Si el administrador se encuentra, lo añade al modelo
            model.addAttribute("administrador", administradorOptional.get());
            // 3. Retorna el nombre lógico de la vista. Spring buscará el archivo
            // en src/main/resources/templates/administrador/detalle.html
            return "administrador/detalle";
        } else {
            // Si el administrador no se encuentra, se añade un mensaje de error y redirige
            model.addAttribute("error", "Administrador no encontrado con ID: " + id);
            return "redirect:/administrador"; // Redirige a la página principal de administradores
        }
    }

}
