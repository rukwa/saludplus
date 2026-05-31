# Microservicio SaludPlus - Pipeline de CI/CD

## Integrantes
* Matias Echeverria
* Benjamin Sandoval

## Garantía de Calidad y Trazabilidad
* **Trasabilidad:** Se logró estructurar y automatizar con éxito el ciclo de vida del microservicio en GitHub Actions bajo el flujo 'SaludPlus CI/CD Pipeline', garantizando la trazabilidad de cada cambio desde el commit hasta las fases de empaquetado. En cada ejecución el pipeline utiliza el "Hash del Commit" de GitHub (${{ github.sha }}) para etiquetar de manera única y permanente la imagen de Docker generada. Esto garantiza que ante cualquier error en producción, el equipo de TI puede saber el commit exacto que causó el problema.

* **Calidad:** El pipeline descarga el código del repositorio, instala las herramientas correspondientes (JDK 17 / Maven) y ejecuta pruebas automatizadas (mvn test) para garantizar que la lógica del microservicio no se rompa con los nuevos cambios.
* **Seguridad (Gobernanza):** El análisis estático de Snyk bloquea de inmediato el pipeline si detecta vulnerabilidades de nivel 'Alto'. El contenedor corre bajo un usuario no-root (`USER 10001`). Otro ejemplo seria cuando dependabot intento proponer actualizaciones con librerias antiguel pipeline las rechazó inmediatamente con una "X" roja para proteger el sistema.
*  **Construccion:** si es que todo el análisis anterior es exitoso, se empaqueta la aplicación usando el Dockerfile y se simula el levantamiento del servicio en un entorno en la nube usando docker compose.

Uso de IA: Se utilizó asistencia de IA (Gemini) para la estructuración de las etapas del pipeline de GitHub Actions y la optimización del Dockerfile
