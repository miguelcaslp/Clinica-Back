package com.miguel.repository;

import com.miguel.model.ClinicAndClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicAndClientRepository extends JpaRepository<ClinicAndClient, Long> {
}
