package com.example.sensor_server.validator;

import com.example.sensor_server.dto.MeasurementDTO;
import com.example.sensor_server.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementDTOValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTOValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTOForValidation = (MeasurementDTO)target;

        String sensorName = measurementDTOForValidation.getSensor().getName();
        if (!sensorService.getByName(sensorName).isPresent()) {
            errors.rejectValue("sensor", "", "Can't find sensor with this name");
        }
    }
}
