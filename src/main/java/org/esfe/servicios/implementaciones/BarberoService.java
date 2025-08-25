package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Barbero;
import org.esfe.repositorios.IBarberoRepository;
import org.esfe.servicios.interfaces.IBarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BarberoService implements IBarberoService {

    @Autowired
    private IBarberoRepository barberoRepository;

        @Override
    public List<Barbero> obtenerTodos() {
        return barberoRepository.findAll();
    }

    @Override
    public Optional<Barbero> buscarPorId(Integer id) {
        return barberoRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public Page<Barbero> buscarConteniendo(String nombre, String apellido, String telefono, Pageable pageable) {
        return barberoRepository.findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCaseOrTelefonoContainingIgnoreCase(nombre, apellido, telefono, pageable);
    }

    @Override
    @Transactional // Para operaciones de escritura
    public Barbero crearOEditar(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    @Override
    @Transactional // Para operaciones de escritura
    public void eliminarPorId(Integer id) {
        barberoRepository.deleteById(id);
    }
}
