package com.miguel.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private java.time.LocalDate appointmentDate;

    @Column(nullable = false)
    private java.time.LocalTime startTime;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room room;

    public Appointment() {
    }

    public Appointment(Long id, LocalDate appointmentDate, LocalTime startTime, Client client, Room room) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.client = client;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
