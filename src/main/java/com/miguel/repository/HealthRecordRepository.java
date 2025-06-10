package com.miguel.repository;

import com.miguel.model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    // Aquí puedes añadir métodos personalizados si es necesario
    // Por ejemplo, encontrar registros de salud por cliente
    List<HealthRecord> findByClientId(Long clientId);

    // Usando una consulta SQL nativa
    @Query(value = "SELECT hr.* " +
            "FROM health_record hr " +
            "JOIN employee e ON hr.employee_id = e.id " +
            "JOIN clinic c ON hr.clinic_id = c.id " +
            "JOIN client cl ON hr.client_id = cl.id " +
            "WHERE cl.id = :clientId " +
            "AND c.id = e.clinic_id " +
            "AND e.id = :employeeId", nativeQuery = true)
    List<HealthRecord> findHealthRecordsByClientAndEmployeeClinic(Long clientId, Long employeeId);
}
