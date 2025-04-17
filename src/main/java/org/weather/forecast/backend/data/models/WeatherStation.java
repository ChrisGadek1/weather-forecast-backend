package org.weather.forecast.backend.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "weather_stations")
public class WeatherStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

    @JsonProperty("sensors_list")
    private String rawStationSensorList;

    @OneToMany(mappedBy = "weatherStation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measure> measures;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public WeatherStation(String name, List<String> sensorsList, List<Measure> measures) {
        this.name = name;
        this.rawStationSensorList = String.join(",", sensorsList);
        this.measures = measures;
    }

    public WeatherStation() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSensorsList() {
        return Arrays.stream(rawStationSensorList.split(",")).toList();
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setRawStationSensorList(String rawStationSensorList) {
        this.rawStationSensorList = rawStationSensorList;
    }
}
