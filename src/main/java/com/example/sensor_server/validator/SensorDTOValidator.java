package com.example.sensor_server.validator;

import com.example.sensor_server.dto.SensorDTO;
import com.example.sensor_server.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorDTOValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTOForValidation = (SensorDTO)target;

        if (sensorService.getByName(sensorDTOForValidation.getName()).isPresent()) {
            errors.rejectValue("name","", "This name already used");
        }
    }
}
