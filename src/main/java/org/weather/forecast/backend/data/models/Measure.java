package org.weather.forecast.backend.data.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "measures")
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String measuredQuantityName;

    @Column(name = "measurement_value")
    private Float value;

    private Timestamp timestamp;

    private String unit;

    @ManyToOne
    @JoinColumn(name = "weather_station_id", referencedColumnName = "id")
    private WeatherStation weatherStation;

    public Measure(String measuredQuantityName, Float value, String unit) {
        this.measuredQuantityName = measuredQuantityName;
        this.value = value;
        this.timestamp = Timestamp.from(Instant.now());
        this.unit = unit;
    }

    public Measure() {}

    public String getMeasuredQuantityName() {
        return measuredQuantityName;
    }

    public Float getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUnit() {
        return unit;
    }

    public Long getId() {
        return id;
    }

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
