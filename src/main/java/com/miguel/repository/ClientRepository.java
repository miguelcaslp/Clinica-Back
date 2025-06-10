package com.miguel.repository;

import com.miguel.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Método para obtener todos los clientes por el ID del administrador y ordenados por clínica
    @Query("SELECT c.id AS clinic_id, c.name AS clinic_name, cl.id AS client_id, cl.firstName, cl.lastName, cl.email, cl.phoneNumber, cl.dni " +
            "FROM Clinic c " +
            "JOIN ClinicAndClient cc ON c.id = cc.clinic.id " + // Relaciona la clínica con el cliente
            "JOIN Client cl ON cc.client.id = cl.id " + // Relaciona el cliente con el ClinicAndClient
            "WHERE c.admin.id = :adminId " + // Filtra por el ID del administrador
            "ORDER BY c.name, cl.lastName, cl.firstName")
    List<Object[]> findClientsByAdminId(Long adminId);

    @Query("SELECT c.id AS clinic_id, c.name AS clinic_name, cl.id AS client_id, cl.firstName, cl.lastName, cl.email, cl.phoneNumber, cl.dni " +
            "FROM Clinic c " +
            "JOIN ClinicAndClient cc ON c.id = cc.clinic.id " + // Relaciona la clínica con el cliente
            "JOIN Client cl ON cc.client.id = cl.id " + // Relaciona el cliente con el ClinicAndClient
            "JOIN Employee e ON e.clinic.id = c.id " + // Relaciona la clínica con el empleado
            "WHERE e.id = :employeeId " + // Filtra por el ID del empleado
            "ORDER BY c.name, cl.lastName, cl.firstName")
    List<Object[]> findClientsByEmployeeId(Long employeeId);


}