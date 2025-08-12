package org.esfe.repositorios;

import org.esfe.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombreOrApellidoOrTelefono(String nombre, String apellido, String telefono);

    Optional<Cliente> findByIdOrderByIdAsc(Integer id);

    List<Cliente> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCaseOrderByNombreAsc(String nombre, String apellido);

    Optional<Cliente> findByTelefonoIgnoreCaseOrderByTelefonoAsc(String telefono);


}
