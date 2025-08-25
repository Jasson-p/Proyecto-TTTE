package org.esfe.controladores;

import org.esfe.modelos.Cita;
import org.esfe.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController // Cambia a RestController para devolver JSON
@RequestMapping("/api/citas") // Un prefijo común para APIs
public class CitaRestController {

    @Autowired
    private ICitaService citaService;

    @GetMapping("/ocupadas")
    public List<String> obtenerHorasOcupadas(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, @RequestParam("barberoId") Integer barberoId) {
        // Usa el servicio para obtener las citas
        List<Cita> citas = citaService.findByFechaAndBarberoId(fecha, barberoId);

        // Extraer las horas de las citas y convertirlas a un formato String
        return citas.stream()
                .map(cita -> cita.getHora().toString())
                .collect(Collectors.toList());
    }
}