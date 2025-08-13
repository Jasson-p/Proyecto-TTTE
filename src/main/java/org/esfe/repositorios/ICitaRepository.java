package org.esfe.repositorios;

import org.esfe.modelos.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICitaRepository extends JpaRepository<Cita, Integer>{
    List<Cita> findByServicioNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(String nombreServicio, String nombreCliente);

    Optional<Cita> findByIdOrderByIdAsc(Integer id);
}
