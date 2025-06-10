package com.miguel.repository;

import com.miguel.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario

    // Consulta personalizada para obtener las citas de una sala y fecha específica
    List<Appointment> findByRoomIdAndAppointmentDate(Long roomId, LocalDate appointmentDate);

    // Consulta personalizada para obtener todas las citas médicas de las clínicas cuyo administrador sea el especificado
    @Query("SELECT a FROM Appointment a " +
            "JOIN a.room r " +
            "JOIN r.clinic c " +
            "JOIN c.admin admin " +
            "WHERE admin.id = :adminId " +
            "ORDER BY c.name, a.appointmentDate, a.startTime")
    List<Appointment> findAppointmentsByAdminId(@Param("adminId") Long adminId);

    // Consulta personalizada para obtener todas las citas médicas de las clínicas en una fecha específica
    @Query("SELECT a FROM Appointment a " +
            "JOIN a.room r " +
            "JOIN r.clinic c " +
            "WHERE a.appointmentDate = :appointmentDate " +
            "AND c.admin.id = :adminId " +  // Filtrar por el admin_id de la clínica
            "ORDER BY c.name, a.startTime")
    List<Appointment> findAppointmentsByDateAndAdmin(@Param("appointmentDate") LocalDate appointmentDate,
                                                     @Param("adminId") Long adminId);



    // Obtener todas las citas médicas de las clínicas del administrador de la clínica del empleado especificado
    @Query("SELECT a FROM Appointment a " +
            "JOIN a.room r " +
            "JOIN r.clinic c " +
            "JOIN c.admin admin " +
            "JOIN Employee e ON e.clinic.id = c.id " +
            "WHERE e.id = :employeeId " +
            "AND admin.id = (SELECT cl.admin.id " +
            "                FROM Employee emp " +
            "                JOIN emp.clinic cl " +
            "                WHERE emp.id = :employeeId) " +
            "ORDER BY c.name, a.appointmentDate, a.startTime")
    List<Appointment> findAppointmentsByEmployeeId(@Param("employeeId") Long employeeId);
    @Query("SELECT a FROM Appointment a " +
            "JOIN a.room r " +
            "JOIN r.clinic c " +
            "JOIN c.admin admin " +
            "JOIN Employee e ON e.clinic.id = c.id " +
            "JOIN a.client cl " +
            "WHERE e.id = :employeeId " +
            "ORDER BY r.roomName, a.appointmentDate, a.startTime")
    List<Appointment> findonlyAppointmentsByEmployeeId(@Param("employeeId") Long employeeId);

    @Query(value = "SELECT " +
            "a.id AS appointment_id, " +
            "a.appointment_date, " +
            "a.start_time, " +
            "a.client_id, " +
            "CONCAT(cl.first_name, ' ', cl.last_name) AS client_name, " +  // Nombre completo del cliente
            "r.id AS room_id, " +
            "r.room_name AS room_name, " +
            "c.id AS clinic_id, " +
            "c.name AS clinic_name, " +
            "ad.id AS admin_id " +  // Sin el nombre del administrador
            "FROM appointment a " +
            "JOIN client cl ON a.client_id = cl.id " + // Incluye cliente
            "JOIN room r ON a.room_id = r.id " +
            "JOIN clinic c ON r.clinic_id = c.id " +
            "JOIN admin ad ON c.admin_id = ad.id " +
            "WHERE ad.id = :adminId " +
            "ORDER BY c.name, a.appointment_date, a.start_time",
            nativeQuery = true)
    List<Object[]> findAppointmentsByAdminIddate(@Param("adminId") Long adminId);

}
