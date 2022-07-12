package com.example.sensor_server.controller;

import com.example.sensor_server.dto.SensorDTO;
import com.example.sensor_server.service.SensorService;
import com.example.sensor_server.validator.SensorDTOValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorRestController {
    private final SensorService sensorService;
    private final SensorDTOValidator sensorDTOValidator;

    @Autowired
    public SensorRestController(SensorService sensorService, SensorDTOValidator sensorDTOValidator) {
        this.sensorService = sensorService;
        this.sensorDTOValidator = sensorDTOValidator;
    }

    @GetMapping()
    public List<SensorDTO> getAllSensors() {
        return sensorService.getAll().stream()
                .map(o->SensorDTO.convertFromSensor(o))
                .collect(Collectors.toList());
    }


    //TODO реализовать выдачу понятных сообщений в ответе а не только кодов ответа
    @PostMapping("/registration")
    @Operation(summary = "Регистрирует новый сенсор", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сенсор зарегистрирован успешно"),
            @ApiResponse(responseCode = "400", description = "Некорретные параметры сенсора"),
            @ApiResponse(responseCode = "409", description = "Переданное имя сенсора уже используется")
    })
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        sensorDTOValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        sensorService.save(sensorDTO.convertToSensor());

        return new ResponseEntity(HttpStatus.OK);
    }
}
