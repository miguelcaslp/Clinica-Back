package com.miguel.service;

import com.miguel.model.AttendanceRecord;
import com.miguel.model.Employee;
import com.miguel.repository.AttendanceRecordRepository;
import com.miguel.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceRecordService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceRecordService(AttendanceRecordRepository attendanceRecordRepository, EmployeeRepository employeeRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordRepository.findAll();
    }

    public Optional<AttendanceRecord> getAttendanceRecordById(Long id) {
        return attendanceRecordRepository.findById(id);
    }

    public AttendanceRecord createAttendanceRecord(AttendanceRecord attendanceRecord) {
        return attendanceRecordRepository.save(attendanceRecord);
    }

    public AttendanceRecord updateAttendanceRecord(Long id, AttendanceRecord attendanceRecordDetails) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id " + id));

        attendanceRecord.setEntryDate(attendanceRecordDetails.getEntryDate());
        attendanceRecord.setExitDate(attendanceRecordDetails.getExitDate());
        attendanceRecord.setEmployee(attendanceRecordDetails.getEmployee());

        return attendanceRecordRepository.save(attendanceRecord);
    }

    public void deleteAttendanceRecord(Long id) {
        attendanceRecordRepository.deleteById(id);
    }
    public AttendanceRecord registerAttendance(Long employeeId, LocalDateTime entryDate, LocalDateTime exitDate) {
        // Verificamos que el empleado exista
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        // Creamos un nuevo objeto AttendanceRecord
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setEmployee(employee); // Asignamos el empleado
        attendanceRecord.setEntryDate(entryDate);
        attendanceRecord.setExitDate(exitDate);

        // Guardamos el registro en la base de datos
        return attendanceRecordRepository.save(attendanceRecord);
    }


    // Registrar la salida del empleado
    public AttendanceRecord registerExit(Long employeeId) {
        // Buscar el último registro de asistencia para este empleado
        Optional<AttendanceRecord> existingRecord = attendanceRecordRepository.findTopByEmployee_IdOrderByEntryDateDesc(employeeId);

        if (existingRecord.isEmpty()) {
            // Si no existe un registro, lanzar una excepción
            throw new IllegalStateException("Este empleado no tiene un registro de entrada.");
        }

        AttendanceRecord attendanceRecord = existingRecord.get();

        if (attendanceRecord.getExitDate() != null) {
            // Si ya tiene fecha de salida, lanzamos una excepción
            throw new IllegalStateException("Este empleado ya registró su salida.");
        }

        // Asignamos la fecha y hora actuales como la salida
        attendanceRecord.setExitDate(LocalDateTime.now());

        // Guardamos el registro actualizado
        return attendanceRecordRepository.save(attendanceRecord);
    }
    public Optional<AttendanceRecord> getUnfinishedAttendanceRecord(Long employeeId) {
        // Buscar el último registro de entrada de este empleado sin salida
        return attendanceRecordRepository.findTopByEmployee_IdAndExitDateIsNullOrderByEntryDateDesc(employeeId);
    }
    public List<AttendanceRecord> getAttendanceRecordsByEmployeeId(Long employeeId) {
        return attendanceRecordRepository.findByEmployee_Id(employeeId);
    }

}