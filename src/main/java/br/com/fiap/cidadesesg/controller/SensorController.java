package br.com.fiap.cidadesesg.controller;

import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import br.com.fiap.cidadesesg.dto.AppDtos.SensorRequest;
import br.com.fiap.cidadesesg.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
@Tag(name = "Sensores Ambientais", description = "Monitoramento de telemetria urbana (qualidade do ar, ruido, agua e energia)")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os sensores ambientais")
    public ResponseEntity<List<EnvironmentalSensor>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sensor por ID")
    public ResponseEntity<EnvironmentalSensor> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova leitura de sensor")
    public ResponseEntity<EnvironmentalSensor> register(@Valid @RequestBody SensorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(req));
    }

    @PatchMapping("/{id}/reading")
    @Operation(summary = "Atualizar valor de leitura de um sensor")
    public ResponseEntity<EnvironmentalSensor> updateReading(@PathVariable String id, @RequestParam Double value) {
        return ResponseEntity.ok(service.updateReading(id, value));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover sensor por ID")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/critical")
    @Operation(summary = "Listar sensores em estado critico")
    public ResponseEntity<List<EnvironmentalSensor>> findCritical() {
        return ResponseEntity.ok(service.findCritical());
    }
}
