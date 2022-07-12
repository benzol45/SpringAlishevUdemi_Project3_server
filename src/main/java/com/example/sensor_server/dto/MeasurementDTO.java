package com.example.sensor_server.dto;

import com.example.sensor_server.entity.Measurement;

import com.example.sensor_server.service.SensorService;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull(message = "Sensor must be specified")
    private SensorDTO sensor;

    @NotNull(message = "Value must be specified")
    @Min(value = -100, message = "It's impossible for outside, VERY cold")
    @Max(value = 100, message = "It's impossible for outside, VERY hot")
    private float value;

    @NotNull(message = "I don't know it's raining or not")
    private boolean raining;

    public MeasurementDTO() {
    }

    public MeasurementDTO(SensorDTO sensor, float value, boolean raining) {
        this.sensor = sensor;
        this.value = value;
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Measurement convertToMeasurement(SensorService sensorService) {
        Measurement measurement = new Measurement();
        measurement.setSensor(sensorService.getByName(this.getSensor().getName()).orElseThrow(() -> new IllegalArgumentException("Can't find sensor")));
        measurement.setValue(this.getValue());
        measurement.setRaining(this.isRaining());

        return measurement;
    }

    public static MeasurementDTO convertFromMeasurement(Measurement measurement) {
        return new MeasurementDTO(new SensorDTO(measurement.getSensor().getName()), measurement.getValue(), measurement.isRaining());
    }
}
