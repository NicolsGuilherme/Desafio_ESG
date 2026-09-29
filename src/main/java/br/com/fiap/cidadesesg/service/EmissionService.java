package br.com.fiap.cidadesesg.service;

import br.com.fiap.cidadesesg.domain.CarbonEmissionRecord;
import br.com.fiap.cidadesesg.dto.AppDtos.EmissionRequest;
import br.com.fiap.cidadesesg.repository.CarbonEmissionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmissionService {

    private final CarbonEmissionRepository repository;

    public EmissionService(CarbonEmissionRepository repository) {
        this.repository = repository;
    }

    public List<CarbonEmissionRecord> listAll() {
        return repository.findAll();
    }

    public CarbonEmissionRecord register(EmissionRequest req) {
        CarbonEmissionRecord record = new CarbonEmissionRecord(
            req.sector(),
            req.cityZone(),
            req.co2Tons(),
            req.reductionTargetTons(),
            req.yearMonth()
        );
        return repository.save(record);
    }

    public double calculateTotalCo2() {
        return repository.findAll().stream()
            .mapToDouble(CarbonEmissionRecord::getCo2Tons)
            .sum();
    }

    public double calculateTotalTargetReduction() {
        return repository.findAll().stream()
            .mapToDouble(CarbonEmissionRecord::getReductionTargetTons)
            .sum();
    }
}
