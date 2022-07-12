package com.example.sensor_server.controller;

import com.example.sensor_server.dto.MeasurementDTO;
import com.example.sensor_server.service.MeasurementService;
import com.example.sensor_server.service.SensorService;
import com.example.sensor_server.validator.MeasurementDTOValidator;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementRestController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final MeasurementDTOValidator measurementDTOValidator;

    @Autowired
    public MeasurementRestController(MeasurementService measurementService, SensorService sensorService, MeasurementDTOValidator measurementDTOValidator) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.measurementDTOValidator = measurementDTOValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementService.getAll().stream()
                .map(o->MeasurementDTO.convertFromMeasurement(o))
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    @Operation(summary = "Передача измерений от сенсоров", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно обработаны"),
            @ApiResponse(responseCode = "400", description = "Переданы некоректные данные"),
            @ApiResponse(responseCode = "404", description = "Не найден указанный сенсор среди зарегистрированных")
    })
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        measurementDTOValidator.validate(measurementDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        measurementService.save(measurementDTO.convertToMeasurement(sensorService));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Map<String,Integer> getRainyDaysCount() {
        return Map.of("rainyDaysCount", measurementService.getRainyDaysCount());
    }
}
