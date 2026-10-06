# Gestión Médica — Backend con Microservicios

Sistema de gestión clínica construido con **arquitectura de microservicios** sobre Spring Cloud. Proyecto en equipo desarrollado en Cibertec (curso DAWI).

## Tecnologías

- Java 17 · Spring Boot 3.5 · Spring Cloud 2025
- Spring Cloud: Config Server, Eureka (service discovery), API Gateway
- MySQL · Spring Data JPA
- JWT + Spring Security (BCrypt)
- RabbitMQ (mensajería entre servicios)
- Prometheus (métricas) y ELK / Logstash (logs)
- Maven multi-módulo

## Arquitectura

| Módulo | Puerto | Función |
|---|---|---|
| `config-server` | 8888 | Configuración centralizada (`config-repo`) |
| `eureka-server` | 8761 | Registro y descubrimiento de servicios |
| `api-gateway` | 8080 | Punto de entrada único, enrutamiento por Eureka y CORS |
| `ms-auth` | 8000 | Login y generación de JWT; publica eventos de login en RabbitMQ |
| `ms-admin` | 8001 | Gestión de médicos, pacientes y medicamentos |
| `ms-clinica` | 8002 | Citas, recetas e historial |
| `ms-reportes` | 8003 | Reportes en PDF; consume eventos de RabbitMQ |
| `ms-historial` | 8005 | Enfermedades |
| `gestion-medica-entity` | — | Librería compartida con las entidades JPA |
| `docker-monitoring` | — | Configuración de Prometheus |
| `docker-elk` | — | Pipeline de Logstash |

```
Cliente (Angular) → API Gateway :8080 → ms-auth / ms-admin / ms-clinica / ms-historial / ms-reportes
                          │                         │
                      Eureka :8761            Config Server :8888
                                                     
ms-auth ──(cola-login)──► RabbitMQ ──► ms-reportes
```

## Cómo ejecutarlo

### Requisitos

- JDK 17 y Maven
- MySQL con la base `gestion_medica`
- RabbitMQ en `localhost:5672` (usuario `guest`)

### Configuración previa

1. Crear la base de datos:
   ```sql
   CREATE DATABASE gestion_medica;
   ```
2. En `config-server/src/main/resources/application.properties`, ajustar `spring.cloud.config.server.native.search-locations` a la ruta local de la carpeta `config-server/config-repo`.
3. Revisar usuario y contraseña de MySQL en `config-server/config-repo/*.properties`.

### Orden de arranque

1. Instalar la librería compartida:
   ```bash
   cd gestion-medica-entity && mvn clean install
   ```
2. Levantar en este orden, cada uno con `mvn spring-boot:run` desde su carpeta:
   1. `config-server`
   2. `eureka-server`
   3. `ms-auth`, `ms-admin`, `ms-clinica`, `ms-historial`, `ms-reportes`
   4. `api-gateway`
3. Verificar los servicios registrados en `http://localhost:8761`.

### Acceso mediante el gateway

El gateway enruta por nombre de servicio en minúsculas, por ejemplo:

```
POST http://localhost:8080/ms-auth/api/auth/login
GET  http://localhost:8080/ms-clinica/api/citas
GET  http://localhost:8080/ms-reportes/api/reportes/citas-pdf
```

Al iniciar `ms-auth` se crea un usuario de prueba de desarrollo.

### Monitoreo (opcional)

Cada servicio expone métricas en `/actuator/prometheus`. La configuración de scraping está en `docker-monitoring/prometheus/prometheus.yml` y el pipeline de logs en `docker-elk/logstash/pipeline/logstash.conf`.

## Mi participación

> **[COMPLETA ESTA SECCIÓN: qué módulos y funciones desarrollaste tú. Ejemplo: "Desarrollé ms-auth (login con JWT y evento a RabbitMQ) y ms-clinica (citas y recetas)".]**

## Mejoras pendientes

- Validar el JWT en el API Gateway y usar una clave compartida y configurable.
- Dockerizar los servicios con `docker-compose`.
- Externalizar credenciales mediante variables de entorno.
- Agregar pruebas por servicio.

## Frontend

Cliente en Angular: **[agregar enlace al repositorio]**

## Autores

**Leandro Coba** — [LinkedIn](https://www.linkedin.com/in/leandro-david-coba-huayas-372426355/) · ldch07@outlook.com
Colaborador: Facundo ([@FGP-bit](https://github.com/FGP-bit))
