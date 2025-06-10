package com.miguel.repository;

import com.miguel.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    Optional<AttendanceRecord> findTopByEmployee_IdOrderByEntryDateDesc(Long employeeId);
    Optional<AttendanceRecord> findTopByEmployee_IdAndExitDateIsNullOrderByEntryDateDesc(Long employeeId);
    List<AttendanceRecord> findByEmployee_Id(Long employeeId);
}