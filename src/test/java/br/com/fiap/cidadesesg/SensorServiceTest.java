package br.com.fiap.cidadesesg;

import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import br.com.fiap.cidadesesg.dto.AppDtos.SensorRequest;
import br.com.fiap.cidadesesg.repository.EnvironmentalSensorRepository;
import br.com.fiap.cidadesesg.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorServiceTest {

    private EnvironmentalSensorRepository repository;
    private SensorService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(EnvironmentalSensorRepository.class);
        service = new SensorService(repository);
    }

    @Test
    @DisplayName("Deve registrar novo sensor e calcular status WARNING quando AQI > 50")
    void testRegisterWarningSensor() {
        SensorRequest req = new SensorRequest("SNS-01", "Zona Sul", "AIR_QUALITY", 65.0, "AQI");
        EnvironmentalSensor saved = new EnvironmentalSensor("SNS-01", "Zona Sul", "AIR_QUALITY", 65.0, "AQI");
        saved.setId("id-123");

        when(repository.save(any(EnvironmentalSensor.class))).thenReturn(saved);

        EnvironmentalSensor result = service.register(req);

        assertNotNull(result);
        assertEquals("WARNING", result.getStatus());
        assertEquals("SNS-01", result.getSensorCode());
        verify(repository, times(1)).save(any(EnvironmentalSensor.class));
    }

    @Test
    @DisplayName("Deve calcular status CRITICAL quando AQI > 100")
    void testCriticalStatusEvaluation() {
        assertEquals("CRITICAL", EnvironmentalSensor.evaluateStatus("AIR_QUALITY", 120.0));
        assertEquals("NORMAL", EnvironmentalSensor.evaluateStatus("AIR_QUALITY", 30.0));
        assertEquals("WARNING", EnvironmentalSensor.evaluateStatus("NOISE_LEVEL", 85.0));
    }

    @Test
    @DisplayName("Deve listar todos os sensores")
    void testListAllSensors() {
        when(repository.findAll()).thenReturn(List.of(
            new EnvironmentalSensor("S1", "Loc1", "AIR_QUALITY", 40.0, "AQI"),
            new EnvironmentalSensor("S2", "Loc2", "NOISE_LEVEL", 50.0, "dB")
        ));

        List<EnvironmentalSensor> list = service.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Deve lancar excecao ao buscar ID inexistente")
    void testFindByIdNotFound() {
        when(repository.findById("invalido")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findById("invalido"));
    }
}
