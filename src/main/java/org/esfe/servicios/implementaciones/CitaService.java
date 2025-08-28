package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Cita;
import org.esfe.repositorios.ICitaRepository;
import org.esfe.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements ICitaService {
    @Autowired
    private ICitaRepository citaRepository;

    @Override
    public List<Cita> obtenerTodos() {
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> findByFechaAndBarberoId(LocalDate fecha, Integer barberoId) {
        return citaRepository.findByFechaAndBarberoId(fecha, barberoId);
    }

    @Override
    public Optional<Cita> buscarPorId(Integer id) {
        return citaRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public Page<Cita> buscarPorNombresDeServicioYCliente(String nombreServicio, String cliente, Pageable pageable) {
        return citaRepository.findByServicioNombreContainingIgnoreCaseAndClienteContainingIgnoreCase(nombreServicio, cliente, pageable);
    }

    @Override
    public Page<Cita> buscarTodosPaginados(Pageable pageable) {
        return citaRepository.findByOrderByClienteDesc(pageable);
    }

    @Override
    public Cita crearOEditar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarPorId(Integer id) {
        citaRepository.deleteById(id);
    }
}
