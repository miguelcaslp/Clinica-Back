package com.miguel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private java.time.LocalDate date;

    @Column(length = 1000)
    private String initialDiagnosis;

    @Column(length = 1000)
    private String finalDiagnosis;

    @Column(length = 2000)
    private String additionalInfo;

    @Column(length = 1000)
    private String bodyParts;

    @ManyToOne
    @JoinColumn(name = "clinicId", referencedColumnName = "id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client client;

    public HealthRecord() {
    }

    public HealthRecord(Long id, LocalDate date, String initialDiagnosis, String finalDiagnosis, String additionalInfo, String bodyParts, Clinic clinic, Employee employee, Client client) {
        this.id = id;
        this.date = date;
        this.initialDiagnosis = initialDiagnosis;
        this.finalDiagnosis = finalDiagnosis;
        this.additionalInfo = additionalInfo;
        this.bodyParts = bodyParts;
        this.clinic = clinic;
        this.employee = employee;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInitialDiagnosis() {
        return initialDiagnosis;
    }

    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }

    public String getFinalDiagnosis() {
        return finalDiagnosis;
    }

    public void setFinalDiagnosis(String finalDiagnosis) {
        this.finalDiagnosis = finalDiagnosis;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(String bodyParts) {
        this.bodyParts = bodyParts;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}