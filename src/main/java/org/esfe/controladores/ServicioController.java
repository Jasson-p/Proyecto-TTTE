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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

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
    public String create(Model model){
        model.addAttribute("servicio", new Servicio());
        return "servicio/create";
    }

    @PostMapping("/save")
    public String save(Servicio servicio, @RequestParam("fileImagen") MultipartFile fileImagen, BindingResult result,
                       Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute(servicio);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "servicio/create";
        }
        if (fileImagen != null && !fileImagen.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String fileName = fileImagen.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(fileImagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                servicio.setImagen(fileName);
                if (servicio.getId() !=null && servicio.getId() > 0) {
                    Servicio servicioData = servicioService.buscarPorId(servicio.getId()).get();
                    if (servicioData != null && servicioData.getImagen() != null) {

                        Path filePathDelete = uploadPath.resolve(servicioData.getImagen());
                        Files.deleteIfExists(filePathDelete);
                    }
                }

            } catch (Exception e) {
                attributes.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
                return "redirect:/servicio";
            }
        }
        servicioService.crearOEditar(servicio);
        attributes.addFlashAttribute("msg", "servicio creado correctamente");
        return "redirect:/servicio";
    }

    @GetMapping("/imagen/{id}")
    @ResponseBody
    public byte[] mostrarImagen(@PathVariable Integer id) {
        try {
            Servicio servicio = servicioService.buscarPorId(id).get();
            String fileName = servicio.getImagen();
            // Construye la ruta completa del archivo
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Verifica si el archivo existe
            if (!Files.exists(filePath)) {
                // Puedes lanzar una excepción o devolver null
                throw new IOException("Archivo no encontrado: " + fileName);
            }

            // Lee todos los bytes del archivo y los devuelve
            return Files.readAllBytes(filePath);

        } catch (IOException e) {
            return new byte[0]; // Devuelve un arreglo vacío si hay un error
        }
    }
}
