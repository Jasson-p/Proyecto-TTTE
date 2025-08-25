package org.esfe.repositorios;

import org.esfe.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICitaRepository extends JpaRepository<Cita, Integer>{
    Page<Cita> findByServicioNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(String nombreServicio, String nombreCliente, Pageable pageable);

    Page<Cita> findByOrderByClienteDesc(Pageable pageable);

    Optional<Cita> findByIdOrderByIdAsc(Integer id);

    List<Cita> findByFechaAndBarberoId(LocalDate fecha, Integer barberoId);
}
