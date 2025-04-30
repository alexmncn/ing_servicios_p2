package com.alexmncn.ing_servicios_p2.respositories;

import com.alexmncn.ing_servicios_p2.models.ParkingRegister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingRegisterRepository extends JpaRepository<ParkingRegister, Long> {
    @Query("SELECT p FROM ParkingRegister p WHERE p.license_plate = :license_plate ORDER BY p.timestamp DESC")
    List<ParkingRegister> findLastRegistersFromLicensePlate(@Param("license_plate") String license_plate, Pageable pageable);
}