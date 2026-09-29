package br.com.fiap.cidadesesg.service;

import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import br.com.fiap.cidadesesg.domain.SustainabilityProject;
import br.com.fiap.cidadesesg.dto.AppDtos.DashboardSummaryResponse;
import br.com.fiap.cidadesesg.repository.CarbonEmissionRepository;
import br.com.fiap.cidadesesg.repository.EnvironmentalSensorRepository;
import br.com.fiap.cidadesesg.repository.SustainabilityProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EnvironmentalSensorRepository sensorRepo;
    private final CarbonEmissionRepository emissionRepo;
    private final SustainabilityProjectRepository projectRepo;

    @Value("${app.environment:development}")
    private String environment;

    public DashboardService(EnvironmentalSensorRepository sensorRepo,
                            CarbonEmissionRepository emissionRepo,
                            SustainabilityProjectRepository projectRepo) {
        this.sensorRepo = sensorRepo;
        this.emissionRepo = emissionRepo;
        this.projectRepo = projectRepo;
    }

    public DashboardSummaryResponse getSummary() {
        List<EnvironmentalSensor> sensors = sensorRepo.findAll();
        List<SustainabilityProject> projects = projectRepo.findAll();

        long totalSensors = sensors.size();
        long criticalSensors = sensors.stream().filter(s -> "CRITICAL".equalsIgnoreCase(s.getStatus())).count();

        double totalCo2 = emissionRepo.findAll().stream().mapToDouble(e -> e.getCo2Tons() != null ? e.getCo2Tons() : 0.0).sum();
        double totalTarget = emissionRepo.findAll().stream().mapToDouble(e -> e.getReductionTargetTons() != null ? e.getReductionTargetTons() : 0.0).sum();

        long totalProjects = projects.size();
        double totalInvested = projects.stream().mapToDouble(p -> p.getInvested() != null ? p.getInvested() : 0.0).sum();
        double avgProgress = projects.isEmpty() ? 0.0 : projects.stream().mapToInt(p -> p.getProgressPercentage() != null ? p.getProgressPercentage() : 0).average().orElse(0.0);

        Map<String, Long> sensorsByType = sensors.stream()
            .collect(Collectors.groupingBy(EnvironmentalSensor::getSensorType, Collectors.counting()));

        Map<String, Long> projectsByStatus = projects.stream()
            .collect(Collectors.groupingBy(SustainabilityProject::getStatus, Collectors.counting()));

        return new DashboardSummaryResponse(
            environment,
            totalSensors,
            criticalSensors,
            totalCo2,
            totalTarget,
            totalProjects,
            totalInvested,
            avgProgress,
            sensorsByType,
            projectsByStatus,
            Instant.now()
        );
    }
}
