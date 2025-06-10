package com.miguel.service;

import com.miguel.model.ClinicAndClient;
import com.miguel.repository.ClinicAndClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicAndClientService {

    private final ClinicAndClientRepository clinicAndClientRepository;

    @Autowired
    public ClinicAndClientService(ClinicAndClientRepository clinicAndClientRepository) {
        this.clinicAndClientRepository = clinicAndClientRepository;
    }

    public ClinicAndClient saveClinicAndClient(ClinicAndClient clinicAndClient) {
        return clinicAndClientRepository.save(clinicAndClient);
    }
}
