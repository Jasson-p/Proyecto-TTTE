package org.esfe.controladores;

import org.esfe.modelos.Cliente;
import org.esfe.servicios.interfaces.IClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size, @RequestParam("nombre") Optional<String> nombre,
                        @RequestParam("apellido") Optional<String> apellido,
                        @RequestParam("telefono") Optional<String> telefono) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        String nombreSearch = nombre.orElse("");
        String apellidoSearch = apellido.orElse("");
        String telefonoSearch = telefono.orElse("");
        Page<Cliente>clientes = clienteService.buscarConteniendo (nombreSearch, apellidoSearch, telefonoSearch, pageable);
        model.addAttribute("clientes", clientes);
        // Para mantener los valores de búsqueda en el formulario HTML cuando la página se recarga
        model.addAttribute("nombreSearch", nombreSearch);
        model.addAttribute("apellidoSearch", apellidoSearch);
        model.addAttribute("telefonoSearch", telefonoSearch);

        int totalPages = clientes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);


        }
        return "cliente/index";
    }
    @GetMapping("/crear")
    public String create(Cliente cliente) {
        return "cliente/crear";
    }

    @PostMapping("/crear")
    public String save(Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute(cliente);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "cliente/crear";
        }

        clienteService.crearOEditar(cliente);
        attributes.addFlashAttribute("msg", "cliente creado correctamente");
        return "redirect:/cliente";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleCliente(@PathVariable Long id, Model model) {
        // 1. Busca el cliente por su ID usando el servicio
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(id.intValue()); // Convertir Long a Integer si tu servicio lo espera

        if (clienteOptional.isPresent()) {
            // 2. Si el cliente se encuentra (el Optional está presente), lo añade al modelo
            model.addAttribute("cliente", clienteOptional.get());
            // 3. Retorna el nombre lógico de la vista. Spring buscará el archivo
            // en src/main/resources/templates/cliente/detalle.html
            return "cliente/detalle";
        } else {
            // Si el cliente no se encuentra, se añade un mensaje de error y redirige
            model.addAttribute("error", "Cliente no encontrado con ID: " + id);
            return "redirect:/cliente"; // Redirige a la página principal de clientes
        }
    }

    @GetMapping("/eliminar/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            attributes.addFlashAttribute("error", "Cliente no encontrado.");
            return "redirect:/cliente";
        }
        model.addAttribute("cliente", cliente.get());
        return "cliente/eliminar";
    }

    @PostMapping("/eliminar")
    public String delete(Cliente cliente, RedirectAttributes attributes) {
        clienteService.eliminarPorId(cliente.getId());
        attributes.addFlashAttribute("msg", "Cliente eliminado correctamente.");
        return "redirect:/cliente";
    }
}
