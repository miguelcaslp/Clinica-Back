package com.miguel.service;

import com.miguel.model.HealthRecord;
import com.miguel.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    public HealthRecord saveHealthRecord(HealthRecord healthRecord) {
        return healthRecordRepository.save(healthRecord);
    }

    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordRepository.findAll();
    }

    public HealthRecord getHealthRecordById(Long id) {
        return healthRecordRepository.findById(id).orElse(null);
    }

    public void deleteHealthRecord(Long id) {
        healthRecordRepository.deleteById(id);
    }

    public List<HealthRecord> getHealthRecordsByClientId(Long clientId) {
        return healthRecordRepository.findByClientId(clientId);
    }
    public List<HealthRecord> getHealthRecords(Long clientId, Long employeeId) {
        // Llamar al método del repositorio para obtener los informes del cliente
        return healthRecordRepository.findHealthRecordsByClientAndEmployeeClinic(clientId, employeeId);
    }
}
