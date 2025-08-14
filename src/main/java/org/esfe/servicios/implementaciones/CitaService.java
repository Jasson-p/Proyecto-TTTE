package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Cita;
import org.esfe.repositorios.ICitaRepository;
import org.esfe.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Optional<Cita> buscarPorId(Integer id) {
        return citaRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public Page<Cita> buscarPorNombresDeServicioYCliente(String nombreServicio, String nombreCliente, Pageable pageable) {
        return citaRepository.findByServicioNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(nombreServicio, nombreCliente, pageable);
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
