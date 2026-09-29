package br.com.fiap.cidadesesg.controller;

import br.com.fiap.cidadesesg.dto.AppDtos.DashboardSummaryResponse;
import br.com.fiap.cidadesesg.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard Executivo", description = "Consolidacao de indicadores ambientais, projetos e metas ESG")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    @Operation(summary = "Obter resumo consolidado de indicadores de sustentabilidade")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }
}
