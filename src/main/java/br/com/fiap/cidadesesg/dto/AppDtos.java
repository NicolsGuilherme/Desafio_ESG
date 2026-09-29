package br.com.fiap.cidadesesg.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Map;

public class AppDtos {

    public record SensorRequest(
        @NotBlank(message = "O codigo do sensor e obrigatorio") String sensorCode,
        @NotBlank(message = "A localizacao e obrigatoria") String location,
        @NotBlank(message = "O tipo do sensor e obrigatorio") String sensorType,
        @NotNull(message = "O valor da leitura e obrigatorio") Double value,
        @NotBlank(message = "A unidade de medida e obrigatoria") String unit
    ) {}

    public record EmissionRequest(
        @NotBlank(message = "O setor e obrigatorio") String sector,
        @NotBlank(message = "A zona da cidade e obrigatoria") String cityZone,
        @NotNull(message = "As emissoes em toneladas de CO2 sao obrigatorias") Double co2Tons,
        @NotNull(message = "A meta de reducao e obrigatoria") Double reductionTargetTons,
        @NotBlank(message = "O mes/ano de referencia e obrigatorio") String yearMonth
    ) {}

    public record ProjectRequest(
        @NotBlank(message = "O nome do projeto e obrigatorio") String name,
        @NotBlank(message = "A categoria e obrigatoria") String category,
        @NotNull(message = "O orcamento e obrigatorio") Double budget,
        @NotNull(message = "O valor investido e obrigatorio") Double invested,
        @NotNull(message = "A reducao estimada de CO2 e obrigatoria") Double expectedCo2ReductionYear,
        @Min(0) @Max(100) Integer progressPercentage,
        String status
    ) {}

    public record DashboardSummaryResponse(
        String environment,
        long totalSensors,
        long criticalSensors,
        double totalCo2EmissionsTons,
        double totalCo2TargetReductionTons,
        long totalSustainabilityProjects,
        double totalInvestedBudget,
        double averageProjectProgress,
        Map<String, Long> sensorsByType,
        Map<String, Long> projectsByStatus,
        Instant generatedAt
    ) {}
}
