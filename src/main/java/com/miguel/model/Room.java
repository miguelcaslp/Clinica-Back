package com.miguel.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "clinicId", referencedColumnName = "id")
    private Clinic clinic;

    public Room() {
    }

    public Room(Long id, String roomName, Clinic clinic) {
        this.id = id;
        this.roomName = roomName;
        this.clinic = clinic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}