package com.alexmncn.ing_servicios_p2;

import com.alexmncn.ing_servicios_p2.respositories.ParkingRegisterRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking_register")
public class RegistroController {
    private final ParkingRegisterRepository parkingRegisterRepository;


}
