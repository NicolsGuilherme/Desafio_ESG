package br.com.fiap.cidadesesg;

import br.com.fiap.cidadesesg.domain.CarbonEmissionRecord;
import br.com.fiap.cidadesesg.dto.AppDtos.EmissionRequest;
import br.com.fiap.cidadesesg.repository.CarbonEmissionRepository;
import br.com.fiap.cidadesesg.service.EmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmissionServiceTest {

    private CarbonEmissionRepository repository;
    private EmissionService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarbonEmissionRepository.class);
        service = new EmissionService(repository);
    }

    @Test
    @DisplayName("Deve calcular soma correta de emissoes de CO2")
    void testCalculateTotalCo2() {
        when(repository.findAll()).thenReturn(List.of(
            new CarbonEmissionRecord("TRANSPORTE", "Zona 1", 1000.0, 800.0, "2026-08"),
            new CarbonEmissionRecord("INDUSTRIA", "Zona 2", 2500.0, 2000.0, "2026-08")
        ));

        double total = service.calculateTotalCo2();
        assertEquals(3500.0, total);

        double totalTarget = service.calculateTotalTargetReduction();
        assertEquals(2800.0, totalTarget);
    }

    @Test
    @DisplayName("Deve registrar medicao de carbono com sucesso")
    void testRegisterEmission() {
        EmissionRequest req = new EmissionRequest("ENERGIA", "Zona Leste", 5000.0, 4200.0, "2026-08");
        CarbonEmissionRecord saved = new CarbonEmissionRecord("ENERGIA", "Zona Leste", 5000.0, 4200.0, "2026-08");
        saved.setId("em-1");

        when(repository.save(any(CarbonEmissionRecord.class))).thenReturn(saved);

        CarbonEmissionRecord res = service.register(req);
        assertNotNull(res);
        assertEquals("ENERGIA", res.getSector());
        assertTrue(res.getCertified());
    }
}
