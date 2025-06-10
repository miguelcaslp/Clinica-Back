package com.miguel.service;

import com.miguel.model.Clinic;
import com.miguel.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    public Optional<Clinic> getClinicById(Long id) {
        return clinicRepository.findById(id);
    }

    public Clinic createClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }



    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }
    // Método para obtener todas las clínicas de un administrador por su ID

    public Clinic updateClinic(Long id, Clinic updatedClinic) {
        return clinicRepository.findById(id).map(clinic -> {
            clinic.setName(updatedClinic.getName());
            clinic.setAddress(updatedClinic.getAddress());
            clinic.setAdmin(updatedClinic.getAdmin());
            return clinicRepository.save(clinic);
        }).orElse(null);
    }
    // Obtener las clínicas por adminId
    public List<Clinic> getClinicsByAdminId(Long adminId) {
        return clinicRepository.findClinicsByAdminId(adminId);
    }

    // Obtener la clínica a la que pertenece un empleado usando employeeId
    public List<Clinic> getClinicsByEmployeeId(Long employeeId) {
        return clinicRepository.findClinicsByEmployeeId(employeeId);
    }

}
