package br.com.fiap.cidadesesg.service;

import br.com.fiap.cidadesesg.domain.SustainabilityProject;
import br.com.fiap.cidadesesg.dto.AppDtos.ProjectRequest;
import br.com.fiap.cidadesesg.repository.SustainabilityProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final SustainabilityProjectRepository repository;

    public ProjectService(SustainabilityProjectRepository repository) {
        this.repository = repository;
    }

    public List<SustainabilityProject> listAll() {
        return repository.findAll();
    }

    public SustainabilityProject findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Projeto com ID '" + id + "' nao foi encontrado."));
    }

    public SustainabilityProject register(ProjectRequest req) {
        SustainabilityProject proj = new SustainabilityProject(
            req.name(),
            req.category(),
            req.budget(),
            req.invested(),
            req.expectedCo2ReductionYear()
        );
        if (req.progressPercentage() != null) {
            proj.setProgressPercentage(req.progressPercentage());
        }
        if (req.status() != null && !req.status().isBlank()) {
            proj.setStatus(req.status());
        }
        return repository.save(proj);
    }

    public SustainabilityProject updateProgress(String id, int progress, String status) {
        SustainabilityProject proj = findById(id);
        proj.setProgressPercentage(progress);
        if (status != null && !status.isBlank()) {
            proj.setStatus(status);
        }
        return repository.save(proj);
    }
}
