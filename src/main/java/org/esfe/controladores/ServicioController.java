package org.esfe.controladores;

import org.springframework.ui.Model;
import org.esfe.modelos.Servicio;
import org.esfe.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                        @RequestParam("nombre") Optional<String> nombre,
                        @RequestParam("precio") Optional<Double>precio) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = (Pageable) PageRequest.of(currentPage, pageSize);
        String nombreSearch = nombre.orElse("");
        Double precioSearch = precio.orElse(null);
        Page<Servicio> servicios = servicioService.buscarPorNombreyPrecio(nombreSearch,  precioSearch, pageable);
        model.addAttribute("servicios", servicios);

        int totalPages = servicios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);


        }
        return "servicio/index";

    }

    @GetMapping("/create")
    public String create(Servicio servicio){
        return "servicio/create";
    }

    @PostMapping("/save")
    public String save(Servicio servicio, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(servicio);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "servicio/create";
        }

        servicioService.crearOEditar(servicio);
        attributes.addFlashAttribute("msg", "Servicio creado correctamente");
        return "redirect:/servicios";
    }
}
