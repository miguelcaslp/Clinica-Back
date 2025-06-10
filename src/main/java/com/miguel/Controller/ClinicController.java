package com.miguel.Controller;

import com.miguel.model.Clinic;
import com.miguel.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    @Autowired
    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        List<Clinic> clinics = clinicService.getAllClinics();
        return new ResponseEntity<>(clinics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long id) {
        return clinicService.getClinicById(id)
                .map(clinic -> new ResponseEntity<>(clinic, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) {
        Clinic createdClinic = clinicService.createClinic(clinic);
        return new ResponseEntity<>(createdClinic, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long id, @RequestBody Clinic updatedClinic) {
        Clinic clinic = clinicService.updateClinic(id, updatedClinic);
        return clinic != null ? ResponseEntity.ok(clinic) : ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/admin/{adminId}")
    public List<Clinic> getClinicsByAdminId(@PathVariable Long adminId) {
        return clinicService.getClinicsByAdminId(adminId);
    }
    // Endpoint para obtener las clínicas por adminId


    // Endpoint para obtener la clínica a la que pertenece un empleado
    @GetMapping("/employee/{employeeId}")
    public List<Clinic> getClinicsByEmployeeId(@PathVariable Long employeeId) {
        return clinicService.getClinicsByEmployeeId(employeeId);
    }
}
