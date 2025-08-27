package org.esfe.controladores;

import org.esfe.modelos.Barbero;
import org.esfe.modelos.Cita;
import org.esfe.modelos.Cliente;
import org.esfe.modelos.Servicio;
import org.esfe.servicios.interfaces.IBarberoService;
import org.esfe.servicios.interfaces.ICitaService;
import org.esfe.servicios.interfaces.IClienteService;
import org.esfe.servicios.interfaces.IServicioService;
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

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private ICitaService citaService;

    @Autowired
    IBarberoService barberoService;

    @Autowired
    IServicioService servicioService;

    @Autowired
    IClienteService clienteService;



    @GetMapping()
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Cita> citas = citaService.buscarTodosPaginados(pageable);
        model.addAttribute("asignaciones", citas);

        int totalPages = citas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cita/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("barbero", barberoService.obtenerTodos());
        model.addAttribute("servicio", servicioService.obtenerTodos());
        model.addAttribute("cliente", clienteService.obtenerTodos());

        return "cita/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer barberoId,
                       @RequestParam Integer servicioId,
                       @RequestParam Integer clienteId,
                       @RequestParam String fecha, // Agrega este parámetro
                       @RequestParam String hora,   // Agrega este parámetro
                       @RequestParam String estado, // Agrega este parámetro
                       RedirectAttributes attributes){

        Barbero barbero = barberoService.buscarPorId(barberoId).orElse(null);
        Servicio servicio = servicioService.buscarPorId(servicioId).orElse(null);
        Cliente cliente = clienteService.buscarPorId(clienteId).orElse(null);

        if(barbero != null && servicio != null && cliente != null){
            Cita cita = new Cita();
            cita.setBarbero(barbero);
            cita.setServicio(servicio);
            cita.setCliente(cliente);

            // Asigna los valores del formulario a la entidad
            cita.setFecha(java.time.LocalDate.parse(fecha)); // Convierte la fecha del formulario
            cita.setHora(java.time.LocalTime.parse(hora));   // Convierte la hora del formulario
            cita.setEstado(estado);

            citaService.crearOEditar(cita);
            attributes.addFlashAttribute("msg", "Asignación creada correctamente");
        } else {
            attributes.addFlashAttribute("error", "Error al crear la cita. Verifique los datos seleccionados.");
        }

        return "redirect:/citas";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Cita cita = citaService.buscarPorId(id).get();
        model.addAttribute("cita", cita);
        return "cita/delete";
    }

    @PostMapping("/delete")
    public String delete(Cita cita, RedirectAttributes attributes){
        citaService.eliminarPorId(cita.getId());
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente");
        return "redirect:/citas";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Cita cita = citaService.buscarPorId(id).get();
        model.addAttribute("barbero", barberoService.obtenerTodos());
        model.addAttribute("servicio", servicioService.obtenerTodos());
        model.addAttribute("cliente", clienteService.obtenerTodos());
        model.addAttribute("cita", cita);
        return "cita/edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam Integer barberoId, @RequestParam Integer servicioId,
                         @RequestParam Integer clienteId,
                         @RequestParam String fecha, // Agrega este parámetro
                         @RequestParam String hora,   // Agrega este parámetro
                         @RequestParam String estado,
                         RedirectAttributes attributes){
        Barbero barbero = barberoService.buscarPorId(barberoId).get();
        Servicio servicio = servicioService.buscarPorId(servicioId).get();
        Cliente cliente = clienteService.buscarPorId(clienteId).get();

        if(barbero != null && servicio != null && cliente !=null){
            Cita cita = new Cita();
            cita.setId(id);
            cita.setBarbero(barbero);
            cita.setServicio(servicio);
            cita.setCliente(cliente);


            // Asigna los valores del formulario a la entidad
            cita.setFecha(java.time.LocalDate.parse(fecha)); // Convierte la fecha del formulario
            cita.setHora(java.time.LocalTime.parse(hora));   // Convierte la hora del formulario
            cita.setEstado(estado);

            citaService.crearOEditar(cita);
            attributes.addFlashAttribute("msg", "Asignación modificada correctamente");
        }

        return "redirect:/citas";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Cita cita = citaService.buscarPorId(id).get();
        model.addAttribute("cita", cita);
        return "cita/details";
    }

}
