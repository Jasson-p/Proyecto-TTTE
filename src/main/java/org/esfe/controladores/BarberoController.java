package org.esfe.controladores;

import org.esfe.modelos.Barbero;
import org.esfe.servicios.interfaces.IBarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/barbero")
public class BarberoController {

    @Autowired
    private IBarberoService barberoService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("nombre") Optional<String> nombre,
                        @RequestParam("apellido") Optional<String> apellido) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        String nombreSearch = nombre.orElse("");
        String ApellidoSearch = apellido.orElse("");
        Page<Barbero> barberos = barberoService.buscarPorNombreYApellidoConteniendo(nombreSearch, ApellidoSearch, pageable);
        model.addAttribute("barberos", barberos);

        int totalPages = barberos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "barbero/index";
    }

}
