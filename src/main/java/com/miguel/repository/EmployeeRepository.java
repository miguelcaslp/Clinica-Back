package com.miguel.repository;

import com.miguel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Aquí puedes añadir métodos personalizados si es necesario
    Employee findByEmail(String email); // Método para encontrar empleado por email
    // Método para encontrar todos los empleados de las clínicas de un administrador por su ID
    @Query("SELECT e FROM Employee e JOIN e.clinic c WHERE c.admin.id = :adminId")
    List<Employee> findAllEmployeesByAdminId(Long adminId);
    @Query("SELECT e FROM Employee e WHERE e.clinic.id = (SELECT c.id FROM Clinic c WHERE c.id = (SELECT e2.clinic.id FROM Employee e2 WHERE e2.id = :employeeId))")
    List<Employee> findAllEmployeesByEmployeeId(Long employeeId);


    // Nueva consulta para obtener todos los empleados de las clínicas asociadas a un administrador
    @Query("SELECT e FROM Employee e JOIN e.clinic c WHERE c.admin.id = :adminId ORDER BY c.name, e.lastName, e.firstName")
    List<Employee> findEmployeesByAdminIdOrdered(Long adminId);
}
