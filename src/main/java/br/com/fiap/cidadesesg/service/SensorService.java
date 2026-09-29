package br.com.fiap.cidadesesg.service;

import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import br.com.fiap.cidadesesg.dto.AppDtos.SensorRequest;
import br.com.fiap.cidadesesg.repository.EnvironmentalSensorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SensorService {

    private final EnvironmentalSensorRepository repository;

    public SensorService(EnvironmentalSensorRepository repository) {
        this.repository = repository;
    }

    public List<EnvironmentalSensor> listAll() {
        return repository.findAll();
    }

    public EnvironmentalSensor findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sensor com ID '" + id + "' nao foi encontrado."));
    }

    public EnvironmentalSensor register(SensorRequest req) {
        EnvironmentalSensor sensor = new EnvironmentalSensor(
            req.sensorCode(),
            req.location(),
            req.sensorType(),
            req.value(),
            req.unit()
        );
        return repository.save(sensor);
    }

    public EnvironmentalSensor updateReading(String id, Double newValue) {
        EnvironmentalSensor sensor = findById(id);
        sensor.setValue(newValue);
        return repository.save(sensor);
    }

    public void delete(String id) {
        EnvironmentalSensor sensor = findById(id);
        repository.delete(sensor);
    }

    public List<EnvironmentalSensor> findCritical() {
        return repository.findByStatus("CRITICAL");
    }
}
