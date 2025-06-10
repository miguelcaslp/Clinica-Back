package com.miguel.Controller;

import com.miguel.model.Appointment;
import com.miguel.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(appointment -> new ResponseEntity<>(appointment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/occupied-times")
    public List<Appointment> getOccupiedTimes(@RequestParam Long roomId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Convertir la fecha de string a LocalDate
        return appointmentService.getAppointmentsByRoomAndDate(roomId, localDate);
    }
    @GetMapping("/search")
    public List<Appointment> getAppointments(
            @RequestParam String option,
            @RequestParam(required = false) Long adminId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate) {

        if (option == null || option.isEmpty()) {
            throw new IllegalArgumentException("Opción inválida o parámetros faltantes");
        }

        return appointmentService.findAppointmentsByOption(option, adminId, appointmentDate);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Appointment> getAppointmentsByEmployeeId(@PathVariable Long employeeId) {
        return appointmentService.getAppointmentsByEmployeeId(employeeId);
    }
    // Endpoint para obtener las citas de la clínica de un empleado en una fecha específica

    @GetMapping("/appointments/employee/{employeeId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByEmployee(@PathVariable Long employeeId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByEmployee(employeeId);
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/appointments/admin/{adminId}")
    public List<Object[]> getAppointmentsByAdminIddate(@PathVariable Long adminId) {
        return appointmentService.getAppointmentsByAdminIddate(adminId);
    }

}
