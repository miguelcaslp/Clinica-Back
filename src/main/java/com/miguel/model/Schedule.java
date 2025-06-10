package com.miguel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private java.time.LocalTime openingTime;

    @Column(nullable = false)
    private java.time.LocalTime closingTime;

    @Column(nullable = false, length = 20)
    private String dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "clinicId", referencedColumnName = "id")
    private Clinic clinic;

    public Schedule() {
    }

    public Schedule(Long id, LocalTime openingTime, LocalTime closingTime, String dayOfWeek, Clinic clinic) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.dayOfWeek = dayOfWeek;
        this.clinic = clinic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}