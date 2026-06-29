# Microservicio SaludPlus - Pipeline de CI/CD

## Integrantes
* Matias Echeverria
* Benjamin Sandoval

## Garantía de Calidad y Trazabilidad
* **Trasabilidad:** Cada build de Docker se etiqueta con el hash del commit de GitHub (`${{ github.sha }}`), permitiendo saber exactamente qué versión del código está corriendo en el contenedor.
* **Calidad:** No se permite ningún despliegue si las pruebas unitarias fallan.
* **Seguridad (Gobernanza):** El análisis estático de Snyk bloquea de inmediato el pipeline si detecta vulnerabilidades de nivel 'Alto'. El contenedor corre bajo un usuario no-root (`USER 10001`).

Uso de IA: Se utilizó asistencia de IA (Gemini) para la estructuración de las etapas del pipeline de GitHub Actions y la optimización del Dockerfile

## EP3 - Observabilidad, Métricas y Cumplimiento Normativo

> **Uso de IA (EP3):** se utilizó asistencia de IA (Claude) para configurar Prometheus/Grafana/Pushgateway, ajustar el gate de cobertura de JaCoCo, integrar el escaneo de seguridad con Trivy, escribir los manifiestos de Kubernetes y redactar esta documentación técnica. Las reflexiones individuales de cada integrante (sección de conclusiones) fueron escritas por el equipo sin apoyo de IA, según lo exige la pauta.

### Herramientas integradas

* **Spring Boot Actuator + Micrometer**: expone métricas del microservicio (requests HTTP, errores, uso de CPU/memoria de la JVM, disponibilidad) en formato Prometheus a través de `/actuator/prometheus`.
* **Prometheus** (puerto `9090`): scrapea esas métricas cada 15s y también scrapea el Pushgateway.
* **Pushgateway** (puerto `9091`): recibe métricas puntuales que el pipeline de CI/CD publica al finalizar cada build (cobertura de pruebas y duración del despliegue), para que también queden visibles en el dashboard.
* **Grafana** (puerto `3000`, usuario `admin` / clave `admin123`): dashboard personalizado, provisionado automáticamente al levantar `docker compose up`, con paneles de disponibilidad, requests por endpoint, tasa de errores, uso de CPU/memoria, cobertura de pruebas y tiempo de despliegue.
* **JaCoCo**: genera el reporte de cobertura y bloquea el build (`mvn test` falla) si la cobertura de líneas cae bajo el mínimo configurado en `pom.xml`.
* **Trivy**: escanea la imagen Docker recién construida en busca de vulnerabilidades críticas con parche disponible, antes de desplegar.
* **Dependabot** (`.github/dependabot.yml`): revisa semanalmente las dependencias de Maven y abre PRs automáticos cuando hay actualizaciones de seguridad.
* **Branch Protection Rules en GitHub**: en `main` se exige que el pipeline pase y que haya al menos una revisión de Pull Request antes de poder hacer merge (configurado manualmente en Settings → Branches, no es algo que viva en el código del repo).

### Cómo se integran en el pipeline CI/CD

1. `test-and-scan` corre `mvn test`, que ejecuta las pruebas y el gate de cobertura de JaCoCo. Si la cobertura es insuficiente, el job falla y el pipeline se detiene aquí.
2. `build-and-deploy` solo corre si `test-and-scan` fue exitoso (`needs: test-and-scan`).
3. Dentro de `build-and-deploy`, Trivy escanea la imagen antes de desplegar. Si encuentra una vulnerabilidad crítica, el job falla y el despliegue nunca ocurre.
4. Si todo pasa, se levanta el microservicio junto a Prometheus, Grafana y Pushgateway con `docker compose up -d`.
5. El pipeline calcula la cobertura real (desde el CSV de JaCoCo) y el tiempo transcurrido del job, y los publica en Pushgateway para que aparezcan en el dashboard de Grafana junto a las métricas en vivo del microservicio.

### Decisiones técnicas basadas en métricas

* Si la cobertura de pruebas cae bajo el mínimo → el pipeline se detiene antes de construir o desplegar nada.
* Si Trivy detecta una vulnerabilidad crítica en la imagen → el pipeline se detiene antes de desplegar.
* Cada imagen queda etiquetada con el SHA del commit (`${{ github.sha }}`), lo que permite trazar exactamente qué versión del código está corriendo.
* El dashboard permite ver en tiempo real si sube la tasa de errores HTTP o el uso de CPU/memoria tras un despliegue, lo que ayuda a decidir si conviene revertir o investigar antes de seguir desplegando.

### Cómo probarlo localmente

```bash
docker compose up -d
# Microservicio:           http://localhost:8080
# Prometheus:               http://localhost:9090
# Grafana (admin/admin123): http://localhost:3000
```

## EP3 - Orquestación con Kubernetes (IE2)

Se eligió **Kubernetes local (kind)** en lugar de un cluster real en GKE/AWS/Azure: cumple el mismo indicador (desplegar en un entorno orquestado, con probes de salud, manejo de réplicas y configuración declarativa) sin requerir una cuenta de nube ni tarjeta de crédito, y permite que el despliegue se ejecute **automáticamente dentro del propio pipeline de CI/CD** (job `k8s-deploy` en `.github/workflows/devops-pipeline.yml`) en vez de ser un paso manual hecho una sola vez en un computador.

Manifiestos en `k8s/`:
* `secret.yaml`: credenciales de la base de datos (solo para este entorno simulado).
* `mysql-deployment.yaml`: Deployment + Service de MySQL.
* `saludplus-deployment.yaml`: Deployment + Service del microservicio. Incluye un `initContainer` que espera a que MySQL esté disponible, probes de `readiness`/`liveness` contra `/actuator/health`, límites de CPU/memoria, y anotaciones `prometheus.io/*` para que un Prometheus desplegado en el cluster pueda descubrirlo automáticamente.

### Probarlo localmente (requiere `kind` y `kubectl` instalados)

```bash
docker build -t saludplus-service:latest .
kind create cluster --name saludplus-cluster
kind load docker-image saludplus-service:latest --name saludplus-cluster
kubectl apply -f k8s/
kubectl rollout status deployment/saludplus-deployment
kubectl port-forward svc/saludplus-service 8080:80
curl http://localhost:8080/actuator/health
```

### En el pipeline (automático)

El job `k8s-deploy` corre después de `build-and-deploy`: crea un cluster `kind` efímero en el runner de GitHub Actions, carga la imagen recién construida, aplica los manifiestos y espera a que el Deployment quede disponible (`kubectl rollout status`). El log de ese job en la pestaña *Actions* del repo es la evidencia de que el despliegue orquestado funciona.


