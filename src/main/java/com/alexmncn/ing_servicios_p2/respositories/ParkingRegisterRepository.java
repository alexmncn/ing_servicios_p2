package com.alexmncn.ing_servicios_p2.respositories;

import com.alexmncn.ing_servicios_p2.models.ParkingRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRegisterRepository extends JpaRepository<ParkingRegister, Long> {

}