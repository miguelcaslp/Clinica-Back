package com.miguel.model;


import jakarta.persistence.*;

@Entity
public class ClinicAndClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "clinicId", nullable = false)
    private Clinic clinic;

    public ClinicAndClient() {
    }

    public ClinicAndClient(Long id, Client client, Clinic clinic) {
        this.id = id;
        this.client = client;
        this.clinic = clinic;
    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
