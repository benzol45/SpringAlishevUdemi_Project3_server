package com.example.sensor_server.repository;

import com.example.sensor_server.entity.Measurement;
import com.example.sensor_server.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllBySensor(Sensor sensor);

    @Query(
            value = "SELECT count(DISTINCT CAST(m.date_time  AS DATE)) FROM measurement AS m WHERE m.raining=true",
            nativeQuery = true)
    Integer countRainingDays();

    @Query(
            value = "SELECT count(DISTINCT CAST(m.date_time  AS DATE)) FROM measurement AS m WHERE m.sensor_id=?1 AND m.raining=true",
            nativeQuery = true)
    Integer countRainingDaysBySensorId(Integer sensor_id);
}
