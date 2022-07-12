package com.example.sensor_server.service;

import com.example.sensor_server.entity.Sensor;
import com.example.sensor_server.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getByName(String name) {
        return sensorRepository.findByName(name);
    }

    public void save(Sensor sensor) {
        sensor.setCreateDate(LocalDateTime.now());
        sensorRepository.save(sensor);
    }
}
