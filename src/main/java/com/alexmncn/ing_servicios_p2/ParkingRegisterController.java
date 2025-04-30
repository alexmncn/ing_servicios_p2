package com.alexmncn.ing_servicios_p2;

import com.alexmncn.ing_servicios_p2.models.ParkingRegister;
import com.alexmncn.ing_servicios_p2.respositories.ParkingRegisterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/parking")
public class ParkingRegisterController {
    private final ParkingRegisterRepository parkingRegisterRepository;

    public ParkingRegisterController(ParkingRegisterRepository parkingRegisterRepository) {
        this.parkingRegisterRepository = parkingRegisterRepository;
    }


    @PostMapping("/new_register")
    public ParkingRegister createRegister(@RequestParam Boolean entry, @RequestParam String license_plate) {
        ParkingRegister new_register = new ParkingRegister(entry, license_plate);
        return parkingRegisterRepository.save(new_register);
    }

    @GetMapping("/coste/{license_plate}")
    public double getParkingCost(@PathVariable String license_plate) {
        List<ParkingRegister> lastRegisters = parkingRegisterRepository.findLastRegistersFromLicensePlate(license_plate, PageRequest.of(0, 2));

        if (lastRegisters.size() < 2) {
            return 0;
        }

        // Verificamos si la primera entrada fue entrada y la segunda salida
        ParkingRegister lastRegister = lastRegisters.get(0);
        ParkingRegister secondLastRegister = lastRegisters.get(1);

        if (lastRegister.isEntry() && !secondLastRegister.isEntry()) {
            double rate = 0.5;

            lastRegister.get1
        } else {
            return 0;
        }
    }

}
