#!/bin/bash
set -euo pipefail
echo "=========================================="
echo "Executando Smoke Tests da API Cidades ESG"
echo "=========================================="

echo "[1/4] Testando Healthcheck..."
curl --fail --show-error http://localhost:8080/actuator/health

echo -e "
[2/4] Listando Sensores..."
curl --fail --show-error http://localhost:8080/api/v1/sensors

echo -e "
[3/4] Listando Emissoes de CO2..."
curl --fail --show-error http://localhost:8080/api/v1/emissions

echo -e "
[4/4] Resumo do Dashboard..."
curl --fail --show-error http://localhost:8080/api/v1/dashboard/summary

echo -e "
=========================================="
echo "Smoke Tests concluidos com sucesso!"
echo "=========================================="
