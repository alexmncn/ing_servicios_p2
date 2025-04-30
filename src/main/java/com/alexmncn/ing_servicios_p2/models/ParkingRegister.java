package com.alexmncn.ing_servicios_p2.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ParkingRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean entry;
    private String license_plate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime timestamp;


    public ParkingRegister(boolean entry, String license_plate) {
        this.entry = entry;
        this.license_plate = license_plate;
    }

    public boolean isEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }
}