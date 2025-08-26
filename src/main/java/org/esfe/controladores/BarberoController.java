package org.esfe.controladores;
import org.springframework.validation.BindingResult;

import org.esfe.modelos.Barbero;
import org.esfe.servicios.interfaces.IBarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.esfe.servicios.utilerias.PdfGeneratorService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Controller
@RequestMapping("/barbero")
public class BarberoController {

    @Autowired
    private IBarberoService barberoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("nombre") Optional<String> nombre,
                        @RequestParam("apellido") Optional<String> apellido,
                        @RequestParam("telefono") Optional<String> telefono) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        String nombreSearch = nombre.orElse("");
        String apellidoSearch = apellido.orElse("");
        String telefonoSearch = telefono.orElse("");
        Page<Barbero> barberos = barberoService.buscarConteniendo(nombreSearch, apellidoSearch, telefonoSearch, pageable);
        model.addAttribute("barberos", barberos);
        // Para mantener los valores de búsqueda en el formulario HTML cuando la página se recarga
        model.addAttribute("nombreSearch", nombreSearch);
        model.addAttribute("apellidoSearch", apellidoSearch);
        model.addAttribute("telefonoSearch", telefonoSearch);


        int totalPages = barberos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "barbero/index";
    }

    @GetMapping("/crear")
    public String create(Barbero barbero) {
        return "barbero/crear";
    }

    @PostMapping("/crear")
    public String save(Barbero barbero, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute(barbero);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "barbero/crear";
        }

        barberoService.crearOEditar(barbero);
        attributes.addFlashAttribute("msg", "barbero creado correctamente");
        return "redirect:/barbero";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleBarbero(@PathVariable Long id, Model model) {
        // 1. Busca el barbero por su ID usando el servicio
        // Ahora se usa buscarPorId que devuelve un Optional<Barbero>
        Optional<Barbero> barberoOptional = barberoService.buscarPorId(id.intValue());

        if (barberoOptional.isPresent()) {
            // 2. Si el barbero se encuentra (el Optional está presente), lo añade al modelo
            model.addAttribute("barbero", barberoOptional.get());
            // 3. Retorna el nombre lógico de la vista. Spring buscará el archivo
            // en src/main/resources/templates/barbero/detalle.html
            return "barbero/detalle";
        } else {
            // Si el barbero no se encuentra, se añade un mensaje de error
            model.addAttribute("error", "Barbero no encontrado con ID: " + id);
            return "redirect:/barbero"; // Redirige a la página principal de barberos
        }
    }


    @GetMapping("/reportegeneral/{visualizacion}")
    public ResponseEntity<byte[]> ReporteGeneral(@PathVariable("visualizacion") String visualizacion) {
        try {
            List<Barbero> barberos = barberoService.obtenerTodos();
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/rpBarberos", "barberos", barberos);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // Agrega el encabezado UNA sola vez, usando el valor de la URL
            headers.add("Content-Disposition", visualizacion + "; filename=reporte_general.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/eliminar/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Barbero> barbero = barberoService.buscarPorId(id);
        if (barbero.isEmpty()) {
            attributes.addFlashAttribute("error", "Barbero no encontrado.");
            return "redirect:/barbero";
        }
        model.addAttribute("barbero", barbero.get());
        return "barbero/eliminar";
    }

    @PostMapping("/eliminar")
    public String delete(Barbero barbero, RedirectAttributes attributes) {
        // No es necesario buscar el barbero en la base de datos de nuevo
        // porque ya tenemos el ID del formulario
        barberoService.eliminarPorId(barbero.getId());
        attributes.addFlashAttribute("msg", "Barbero eliminado correctamente.");
        return "redirect:/barbero";
    }

}
