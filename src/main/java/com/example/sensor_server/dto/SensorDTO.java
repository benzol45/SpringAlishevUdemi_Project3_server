package com.example.sensor_server.dto;

import com.example.sensor_server.entity.Sensor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SensorDTO {
    @NotBlank(message = "Name must be specified")
    @Size(min = 3, max = 30, message = "Name must be between 3 to 30 chars")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sensor convertToSensor() {
        Sensor sensor = new Sensor();
        sensor.setName(this.getName());
        return sensor;
    }

    public static SensorDTO convertFromSensor(Sensor sensor) {
        return new SensorDTO(sensor.getName());
    }
}