package com.miguel.Controller;

import com.miguel.model.AttendanceRecord;
import com.miguel.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendance-records")
public class AttendanceRecordController {

    private final AttendanceRecordService attendanceRecordService;

    @Autowired
    public AttendanceRecordController(AttendanceRecordService attendanceRecordService) {
        this.attendanceRecordService = attendanceRecordService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords() {
        List<AttendanceRecord> attendanceRecords = attendanceRecordService.getAllAttendanceRecords();
        return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecordById(@PathVariable Long id) {
        return attendanceRecordService.getAttendanceRecordById(id)
                .map(attendanceRecord -> new ResponseEntity<>(attendanceRecord, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AttendanceRecord> createAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) {
        AttendanceRecord createdAttendanceRecord = attendanceRecordService.createAttendanceRecord(attendanceRecord);
        return new ResponseEntity<>(createdAttendanceRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(@PathVariable Long id, @RequestBody AttendanceRecord attendanceRecordDetails) {
        AttendanceRecord updatedAttendanceRecord = attendanceRecordService.updateAttendanceRecord(id, attendanceRecordDetails);
        return new ResponseEntity<>(updatedAttendanceRecord, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable Long id) {
        attendanceRecordService.deleteAttendanceRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Endpoint para registrar la entrada de un empleado
    // Endpoint para registrar la entrada de un empleado
    @PostMapping("/entry/{employeeId}")
    public ResponseEntity<AttendanceRecord> registerEntry(@PathVariable Long employeeId) {
        try {
            // Verificamos si ya existe un registro de entrada sin salida
            Optional<AttendanceRecord> existingRecord = attendanceRecordService.getUnfinishedAttendanceRecord(employeeId);

            // Si existe un registro de entrada sin salida, devolver una respuesta con la entrada pendiente
            if (existingRecord.isPresent()) {
                AttendanceRecord record = existingRecord.get();
                // Devolver el registro actual sin error
                return ResponseEntity.ok(record); // Esto es importante, ya que no se lanza un error, solo se actualiza la UI
            }

            // Llamamos al servicio para registrar la entrada del empleado
            AttendanceRecord attendanceRecord = attendanceRecordService.registerAttendance(employeeId, LocalDateTime.now(), null);

            // Si el registro es exitoso, devolvemos el objeto en la respuesta con estado OK (200)
            return ResponseEntity.ok(attendanceRecord);
        } catch (IllegalArgumentException e) {
            // Si el empleado no es encontrado, devolvemos una respuesta con estado BadRequest (400) y un mensaje
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // En caso de cualquier otro error inesperado, devolvemos un estado interno del servidor (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // Endpoint para registrar la salida de un empleado
    @PostMapping("/exit/{employeeId}")
    public ResponseEntity<?> registerExit(@PathVariable Long employeeId) {
        try {
            AttendanceRecord attendanceRecord = attendanceRecordService.registerExit(employeeId);
            return ResponseEntity.ok(attendanceRecord);
        } catch (IllegalStateException e) {
            // En caso de error, devolver mensaje con BadRequest
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Para cualquier otra excepción inesperada, respondemos con el mensaje del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceRecord>> getAttendanceRecordsByEmployeeId(
            @PathVariable Long employeeId) {

        // Llamamos al servicio para obtener los registros de asistencia
        List<AttendanceRecord> attendanceRecords = attendanceRecordService.getAttendanceRecordsByEmployeeId(employeeId);

        // Verificamos si se encontraron registros
        if (attendanceRecords.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay registros, respondemos con 204
        }

        // Si se encuentran registros, respondemos con 200 y los registros
        return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
    }
}

