# =========================================================
# Multi-Stage Dockerfile - Cidades ESG Inteligentes
# FIAP - Fase 6: DevOps
# Integrantes: Luan Chaves, Matheus Nicacio, Pietro, Nicolas
# =========================================================

# Stage 1: Build & Package
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Cache de dependencias do Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia do codigo-fonte e build do pacote sem testes lentos no build final
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime Minimal & Secure
FROM eclipse-temurin:21-jre-alpine AS runner

LABEL maintainer="FIAP DevOps Grupo 35"
LABEL description="Cidades ESG Inteligentes - Microservico de Monitoramento Urbano"
LABEL version="1.0.0"

# Instalacao de utilitarios minimos para monitoramento
RUN apk add --no-cache curl wget dumb-init

WORKDIR /app

# Criacao de usuario nao-root por boas praticas de seguranca (Principio do Menor Privilegio)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copia do artefato JAR gerado no Stage 1
COPY --from=builder /build/target/cidades-esg-inteligentes-1.0.0.jar ./app.jar

# Ajuste de permissoes
RUN chown -R appuser:appgroup /app

# Troca para o usuario nao-privilegiado
USER appuser:appgroup

# Variaveis de ambiente padrao
ENV PORT=8080 \
    APP_ENVIRONMENT=production \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

# Exposicao da porta da aplicacao
EXPOSE 8080

# Healthcheck nativo integrado ao Spring Boot Actuator
HEALTHCHECK --interval=20s --timeout=5s --start-period=25s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT}/actuator/health || exit 1

# Execucao com dumb-init para gerenciamento correto de sinais POSIX (SIGTERM/SIGINT)
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
