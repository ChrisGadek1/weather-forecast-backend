package org.weather.forecast.backend.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.weather.forecast.backend.data.models.Measure;

public interface MeasureRepository extends CrudRepository<Measure, Integer> {
}
