package br.com.fiap.cidadesesg.repository;

import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface EnvironmentalSensorRepository extends MongoRepository<EnvironmentalSensor, String> {
    Optional<EnvironmentalSensor> findBySensorCode(String sensorCode);
    List<EnvironmentalSensor> findBySensorType(String sensorType);
    List<EnvironmentalSensor> findByStatus(String status);
    long countByStatus(String status);
}
