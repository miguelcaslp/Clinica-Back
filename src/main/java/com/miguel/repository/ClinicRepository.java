package com.miguel.repository;

import com.miguel.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByAdminId(Long adminId);
    // Aquí puedes agregar métodos personalizados si es necesario
    // Obtener todas las clínicas asociadas a un administrador usando adminId
    @Query("SELECT c FROM Clinic c WHERE c.admin.id = :adminId")
    List<Clinic> findClinicsByAdminId(@Param("adminId") Long adminId);

    // Obtener la clínica a la que pertenece un empleado usando employeeId
    @Query("SELECT c FROM Clinic c WHERE c.id = (SELECT e.clinic.id FROM Employee e WHERE e.id = :employeeId)")
    List<Clinic> findClinicsByEmployeeId(@Param("employeeId") Long employeeId);
}
