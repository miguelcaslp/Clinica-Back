package com.miguel.service;

import com.miguel.model.Appointment;
import com.miguel.model.Room;
import com.miguel.repository.AppointmentRepository;
import com.miguel.repository.EmployeeRepository;
import com.miguel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, EmployeeRepository employeeRepository, RoomRepository roomRepository) {
        this.appointmentRepository = appointmentRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));

        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setStartTime(appointmentDetails.getStartTime());
        appointment.setClient(appointmentDetails.getClient());
        appointment.setRoom(appointmentDetails.getRoom());

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    public List<Appointment> getAppointmentsByRoomAndDate(Long roomId, LocalDate date) {
        return appointmentRepository.findByRoomIdAndAppointmentDate(roomId, date);
    }
    public List<Appointment> findAppointmentsByOption(String option, Long adminId, LocalDate appointmentDate) {
        if ("byAdmin".equalsIgnoreCase(option) && adminId != null) {
            // Filtrar citas por el adminId
            return appointmentRepository.findAppointmentsByAdminId(adminId);
        } else if ("byDate".equalsIgnoreCase(option) && appointmentDate != null && adminId != null) {
            // Filtrar citas por fecha y adminId
            return appointmentRepository.findAppointmentsByDateAndAdmin(appointmentDate, adminId);
        } else {
            throw new IllegalArgumentException("Opción inválida o parámetros faltantes");
        }
    }

    public List<Appointment> getAppointmentsByEmployeeId(Long employeeId) {
        return appointmentRepository.findAppointmentsByEmployeeId(employeeId);
    }
    public List<Appointment> getAppointmentsByEmployee(Long employeeId) {
        return appointmentRepository.findonlyAppointmentsByEmployeeId(employeeId);
    }
    // Método que obtiene las citas médicas del administrador especificado
    public List<Object[]> getAppointmentsByAdminIddate(Long adminId) {
        return appointmentRepository.findAppointmentsByAdminIddate(adminId);
    }
}
