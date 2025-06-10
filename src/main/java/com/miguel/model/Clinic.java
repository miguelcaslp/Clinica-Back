package com.miguel.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    private Admin admin;

    public Clinic() {
    }

    public Clinic(Long id, String name, String address, Admin admin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}