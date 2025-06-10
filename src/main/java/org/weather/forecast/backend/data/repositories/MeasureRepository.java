package org.weather.forecast.backend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.weather.forecast.backend.data.dto.MeasureResponseDTO;
import org.weather.forecast.backend.data.models.Measure;

import java.sql.Timestamp;
import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {
    @Query(value = "SELECT new org.weather.forecast.backend.data.dto.MeasureResponseDTO(m.measuredQuantityName, m.value, m.timestamp, m.unit, m.weatherStation.name, m.weatherStation.id, m.id) FROM Measure m WHERE m.timestamp <= :to AND m.timestamp >= :from AND m.weatherStation.id = :stationId")
    List<MeasureResponseDTO> findByTimestampsAndStationId(@Param("from") Timestamp from, @Param("to") Timestamp to, @Param("stationId") long stationId);

    @Query(value = "SELECT new org.weather.forecast.backend.data.dto.MeasureResponseDTO(m.measuredQuantityName, m.value, m.timestamp, m.unit, m.weatherStation.name, m.weatherStation.id, m.id) FROM Measure m WHERE m.timestamp <= :to AND m.timestamp >= :from")
    List<MeasureResponseDTO> findByTimestamps(@Param("from") Timestamp from, @Param("to") Timestamp to);
}
