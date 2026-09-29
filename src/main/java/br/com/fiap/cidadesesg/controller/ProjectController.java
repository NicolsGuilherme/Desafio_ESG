package br.com.fiap.cidadesesg.controller;

import br.com.fiap.cidadesesg.domain.SustainabilityProject;
import br.com.fiap.cidadesesg.dto.AppDtos.ProjectRequest;
import br.com.fiap.cidadesesg.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Projetos de Sustentabilidade", description = "Gestao de iniciativas verdes e orcamentos ESG")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar projetos de sustentabilidade")
    public ResponseEntity<List<SustainabilityProject>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID")
    public ResponseEntity<SustainabilityProject> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo projeto de sustentabilidade")
    public ResponseEntity<SustainabilityProject> register(@Valid @RequestBody ProjectRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(req));
    }

    @PatchMapping("/{id}/progress")
    @Operation(summary = "Atualizar progresso percentual e status do projeto")
    public ResponseEntity<SustainabilityProject> updateProgress(
        @PathVariable String id,
        @RequestParam int progress,
        @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(service.updateProgress(id, progress, status));
    }
}
