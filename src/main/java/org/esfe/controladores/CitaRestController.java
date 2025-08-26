package org.esfe.controladores;

import org.esfe.modelos.Cita;
import org.esfe.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class CitaRestController {

    @Autowired
    private ICitaService citaService;

    @GetMapping("/ocupadas")
    public List<String> obtenerHorasOcupadas(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, @RequestParam("barberoId") Integer barberoId) {

        // Verifica si la fecha es un domingo
        if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return Collections.emptyList(); // Si es domingo, devuelve una lista vacía de horas disponibles
        }

        List<Cita> citas = citaService.findByFechaAndBarberoId(fecha, barberoId);

        return citas.stream()
                .map(cita -> cita.getHora().toString())
                .collect(Collectors.toList());
    }
}