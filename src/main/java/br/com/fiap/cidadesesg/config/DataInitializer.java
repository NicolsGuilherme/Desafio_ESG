package br.com.fiap.cidadesesg.config;

import br.com.fiap.cidadesesg.domain.CarbonEmissionRecord;
import br.com.fiap.cidadesesg.domain.EnvironmentalSensor;
import br.com.fiap.cidadesesg.domain.SustainabilityProject;
import br.com.fiap.cidadesesg.repository.CarbonEmissionRepository;
import br.com.fiap.cidadesesg.repository.EnvironmentalSensorRepository;
import br.com.fiap.cidadesesg.repository.SustainabilityProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final EnvironmentalSensorRepository sensorRepo;
    private final CarbonEmissionRepository emissionRepo;
    private final SustainabilityProjectRepository projectRepo;

    public DataInitializer(EnvironmentalSensorRepository sensorRepo,
                           CarbonEmissionRepository emissionRepo,
                           SustainabilityProjectRepository projectRepo) {
        this.sensorRepo = sensorRepo;
        this.emissionRepo = emissionRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    public void run(String... args) {
        try {
            if (sensorRepo.count() == 0) {
                log.info("Populando base inicial de sensores ESG...");
                sensorRepo.saveAll(List.of(
                    new EnvironmentalSensor("SNS-AR-001", "Avenida Paulista, SP", "AIR_QUALITY", 42.5, "AQI"),
                    new EnvironmentalSensor("SNS-AR-002", "Centro Historico, Vitoria", "AIR_QUALITY", 85.0, "AQI"),
                    new EnvironmentalSensor("SNS-RUI-003", "Zona Portuaria, Santos", "NOISE_LEVEL", 68.0, "dB"),
                    new EnvironmentalSensor("SNS-ENE-004", "Parque Tecnologico, Campinas", "ENERGY_CONSUMPTION", 310.0, "kWh")
                ));
            }

            if (emissionRepo.count() == 0) {
                log.info("Populando inventario de emissoes de carbono...");
                emissionRepo.saveAll(List.of(
                    new CarbonEmissionRecord("TRANSPORTE", "Zona Sul", 12500.0, 10000.0, "2026-08"),
                    new CarbonEmissionRecord("INDUSTRIA", "Distrito Industrial", 28000.0, 22000.0, "2026-08"),
                    new CarbonEmissionRecord("RESIDUOS", "Aterro Sanitario Central", 6400.0, 5000.0, "2026-08")
                ));
            }

            if (projectRepo.count() == 0) {
                log.info("Populando projetos de sustentabilidade...");
                SustainabilityProject p1 = new SustainabilityProject("Frota Eletrica Municipal", "MOBILIDADE_VERDE", 1500000.0, 950000.0, 3200.0);
                p1.setProgressPercentage(65);
                p1.setStatus("EM_EXECUCAO");

                SustainabilityProject p2 = new SustainabilityProject("Parque Solar Comunitario", "ENERGIA_RENOVAVEL", 2200000.0, 2200000.0, 5800.0);
                p2.setProgressPercentage(100);
                p2.setStatus("CONCLUIDO");

                SustainabilityProject p3 = new SustainabilityProject("Reflorestamento Cinturao Verde", "REFLORESTAMENTO", 800000.0, 300000.0, 1400.0);
                p3.setProgressPercentage(35);
                p3.setStatus("EM_EXECUCAO");

                projectRepo.saveAll(List.of(p1, p2, p3));
            }
            log.info("Inicializacao de dados ESG concluida com sucesso!");
        } catch (Exception e) {
            log.warn("Aviso ao conectar ao MongoDB para inicializacao: {}", e.getMessage());
        }
    }
}
