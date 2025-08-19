package org.esfe.controladores;

import org.springframework.ui.Model;
import org.esfe.modelos.Servicio;
import org.esfe.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre,
                        @RequestParam(value = "precio", required = false) Double precio) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = (Pageable) PageRequest.of(currentPage, pageSize);

        Page<Servicio> servicios = servicioService.buscarPorNombreyPrecio(nombre,  precio, pageable);
        model.addAttribute("servicios", servicios);

        return "servicios/index";

    }
}
