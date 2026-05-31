# Microservicio SaludPlus - Pipeline de CI/CD

## Integrantes
* Matias Echeverria
* Benjamin Sandoval

## Garantía de Calidad y Trazabilidad
* **Trasabilidad:** Cada build de Docker se etiqueta con el hash del commit de GitHub (`${{ github.sha }}`), permitiendo saber exactamente qué versión del código está corriendo en el contenedor.
* **Calidad:** No se permite ningún despliegue si las pruebas unitarias fallan.
* **Seguridad (Gobernanza):** El análisis estático de Snyk bloquea de inmediato el pipeline si detecta vulnerabilidades de nivel 'Alto'. El contenedor corre bajo un usuario no-root (`USER 10001`).

Uso de IA: Se utilizó asistencia de IA (Gemini) para la estructuración de las etapas del pipeline de GitHub Actions y la optimización del Dockerfile
