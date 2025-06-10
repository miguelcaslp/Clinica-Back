package com.miguel.Controller;

import com.miguel.model.HealthRecord;
import com.miguel.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health-records")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping
    public ResponseEntity<HealthRecord> createHealthRecord(@RequestBody HealthRecord healthRecord) {
        HealthRecord savedHealthRecord = healthRecordService.saveHealthRecord(healthRecord);
        return ResponseEntity.ok(savedHealthRecord);
    }

    @GetMapping
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordService.getAllHealthRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthRecord> getHealthRecordById(@PathVariable Long id) {
        HealthRecord healthRecord = healthRecordService.getHealthRecordById(id);
        if (healthRecord != null) {
            return ResponseEntity.ok(healthRecord);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable Long id) {
        healthRecordService.deleteHealthRecord(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public List<HealthRecord> getHealthRecordsByClientId(@PathVariable Long clientId) {
        return healthRecordService.getHealthRecordsByClientId(clientId);
    }
    @GetMapping("/client/{clientId}/employee/{employeeId}")
    public List<HealthRecord> getHealthRecords(@PathVariable Long clientId, @PathVariable Long employeeId) {
        return healthRecordService.getHealthRecords(clientId, employeeId);
    }
}
