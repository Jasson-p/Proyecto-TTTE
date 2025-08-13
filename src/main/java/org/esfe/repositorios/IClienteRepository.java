package org.esfe.repositorios;

import org.esfe.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {


    Optional<Cliente> findByIdOrderByIdAsc(Integer id);


    Optional<Cliente> findByTelefonoIgnoreCaseOrderByTelefonoAsc(String telefono);


    Page<Cliente> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(String nombre, String apellido, Pageable pageable);
}
