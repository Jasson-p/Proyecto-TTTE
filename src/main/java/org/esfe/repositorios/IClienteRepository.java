package org.esfe.repositorios;

import org.esfe.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombreOrApellidoOrTelefono(String nombre, String apellido, String telefono);
}
