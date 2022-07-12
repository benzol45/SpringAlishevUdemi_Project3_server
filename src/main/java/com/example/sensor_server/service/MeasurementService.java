package com.example.sensor_server.service;

import com.example.sensor_server.entity.Measurement;
import com.example.sensor_server.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public void save(Measurement measurement) {
        measurement.setDateTime(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    public int getRainyDaysCount() {
        return measurementRepository.countRainingDays();
    }
}
