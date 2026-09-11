# LibreriaSpring

API REST con Spring Boot, PostgreSQL, JPA y Swagger. Java y Maven se ejecutan dentro de Docker.

## Arranque

1. Copia `.env.example` como `.env` y cambia la contrasena.
2. Construye y levanta los servicios:

```powershell
docker compose up -d --build
```

3. Comprueba el estado:

```powershell
docker compose ps
```

La API queda disponible en `http://localhost:9090` y Swagger en `http://localhost:9090/swagger-ui/index.html`.

## Endpoints principales

- `GET /api/libros`
- `POST /api/libros`
- `DELETE /api/libros/{id}`
- `GET /api/usuarios`
- `POST /api/usuarios`
- `POST /api/libros/{libroId}/reservar/{usuarioId}`

## Ejemplo de creacion

```json
{
  "nombre": "Clean Code",
  "autor": "Robert C. Martin",
  "anio": 2008
}
```

Para detener los servicios:

```powershell
docker compose down
```

El volumen `postgres_data` conserva los datos al detener los contenedores. No uses `docker compose down -v` salvo que quieras borrar la base de datos.
