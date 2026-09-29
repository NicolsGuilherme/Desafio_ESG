#!/bin/bash
set -e
echo "=========================================="
echo "Iniciando Deploy no Ambiente de STAGING..."
echo "=========================================="
docker compose -p cidades-esg-staging -f docker-compose.staging.yml down
docker compose -p cidades-esg-staging -f docker-compose.staging.yml up -d --build --wait
curl --fail --show-error --retry 12 --retry-delay 5 --retry-all-errors http://localhost:8081/actuator/health
curl --fail --show-error http://localhost:8081/api/v1/dashboard/summary
echo "Staging saudavel (UP)."
