# Microservicio SaludPlus - Pipeline de CI/CD

## Integrantes
* Matias Echeverria
* Benjamin Sandoval

## Garantía de Calidad y Trazabilidad
* **Trasabilidad:** Se logró estructurar y automatizar con éxito el ciclo de vida del microservicio en GitHub Actions bajo el flujo 'SaludPlus CI/CD Pipeline', garantizando la trazabilidad de cada cambio desde el commit hasta las fases de empaquetado.

* **Calidad:** No se permite ningún despliegue si las pruebas unitarias fallan.
* **Seguridad (Gobernanza):** El análisis estático de Snyk bloquea de inmediato el pipeline si detecta vulnerabilidades de nivel 'Alto'. El contenedor corre bajo un usuario no-root (`USER 10001`).

Uso de IA: Se utilizó asistencia de IA (Gemini) para la estructuración de las etapas del pipeline de GitHub Actions y la optimización del Dockerfile
