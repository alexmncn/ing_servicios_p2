package com.alexmncn.ing_servicios_p2;

import com.alexmncn.ing_servicios_p2.models.ParkingRegister;
import com.alexmncn.ing_servicios_p2.respositories.ParkingRegisterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GetMapping("/cost/{license_plate}")
    public ResponseEntity<Map<String, Object>> getParkingCost(@PathVariable String license_plate) {
        List<ParkingRegister> lastRegisters = parkingRegisterRepository.findLastRegistersFromLicensePlate(license_plate, PageRequest.of(0, 2));
        Map<String, Object> response = new HashMap<>();
        HttpStatus code = HttpStatus.OK;

        if (lastRegisters.size() < 2) {
            ParkingRegister lastRegister = lastRegisters.getFirst();
            if (lastRegister != null) {
                if (lastRegister.isEntry()) {
                    response.put("error", "El vehículo ha entrado pero aún no ha salido");
                    code = HttpStatus.CONFLICT;
                } else {
                    response.put("error", "El vehículo ha salido pero no se ha registrado una entrada anterior");
                    code = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            } else {
                response.put("error", "No se ha registrado ninguna entrada con esa matrícula");
                code = HttpStatus.NOT_FOUND;
            }

            return new ResponseEntity<>(response, code);
        }

        // Verificamos si la primera entrada fue entrada y la segunda salida
        ParkingRegister lastRegister = lastRegisters.get(0);
        ParkingRegister secondLastRegister = lastRegisters.get(1);

        if (!lastRegister.isEntry() && secondLastRegister.isEntry()) {
            double base = 1.0;
            double rate = 0.035; // Cost per minute (euros)

            LocalDateTime entryDate = secondLastRegister.getTimestamp();
            LocalDateTime exitDate = lastRegister.getTimestamp();
            Duration duration = Duration.between(entryDate, exitDate);
            long durationMinutes = duration.toMinutes();
            double cost = durationMinutes * rate;

            BigDecimal bd = new BigDecimal(cost);
            cost = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            cost = cost + base;

            response.put("coste", cost);
            return new ResponseEntity<>(response, code);
        } else {
            if (lastRegister.isEntry()) {
                response.put("error", "El vehículo ha entrado pero aún no ha salido");
                code = HttpStatus.CONFLICT;
            } else {
                response.put("error", "El vehículo ha salido pero no se ha registrado una entrada anterior");
                code = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return new ResponseEntity<>(response, code);
        }
    }

}
