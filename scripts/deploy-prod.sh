#!/bin/bash
set -e
echo "=========================================="
echo "Iniciando Deploy no Ambiente de PRODUCAO..."
echo "=========================================="
docker compose -p cidades-esg-production -f docker-compose.prod.yml down
docker compose -p cidades-esg-production -f docker-compose.prod.yml up -d --build --wait
curl --fail --show-error --retry 12 --retry-delay 5 --retry-all-errors http://localhost:8080/actuator/health
curl --fail --show-error http://localhost:8080/api/v1/dashboard/summary
echo "Producao academica saudavel (UP)."
