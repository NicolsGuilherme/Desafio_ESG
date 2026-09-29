package br.com.fiap.cidadesesg.controller;

import br.com.fiap.cidadesesg.domain.CarbonEmissionRecord;
import br.com.fiap.cidadesesg.dto.AppDtos.EmissionRequest;
import br.com.fiap.cidadesesg.service.EmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emissions")
@Tag(name = "Emissoes de Carbono", description = "Controle de pegada de carbono e metas de reducao urbana")
public class EmissionController {

    private final EmissionService service;

    public EmissionController(EmissionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar registros de emissao de CO2")
    public ResponseEntity<List<CarbonEmissionRecord>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    @Operation(summary = "Registrar nova medicao de emissoes setoriais")
    public ResponseEntity<CarbonEmissionRecord> register(@Valid @RequestBody EmissionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(req));
    }
}
