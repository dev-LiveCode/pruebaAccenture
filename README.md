# Franquicia API 🏬

API REST para la gestión de franquicias, sucursales y productos.  
Desarrollada como prueba técnica para el rol de Backend Developer en **Accenture**.

---

## 🧱 Arquitectura del Proyecto

El proyecto sigue los principios de **Clean Architecture**, **SOLID** y **Clean Code**, usando programación **reactiva** con Spring WebFlux para máxima escalabilidad y eficiencia.

```
src/main/java/com/victorvivas/pruebaaccenture/accenture_franquicia_api
│
├── domain/             → Lógica de negocio
│   ├── model/          → Entidades: Franchise, Branch, Product
│   └── repository/     → Interfaces de persistencia (puertos)
│
├── infrastructure/     → Adaptadores secundarios como MongoDB
│   ├── exception/    → Manejador de excepciones
│   └── persistence/    → Repositorios reactivos e implementación
│
├── entrypoints/
│   ├── controller/           → Controladores API REST
│   └── dto/           → Objetos de transferencia de datos
│
└── resources/           → Application.properties
```

---

## ⚙️ Tecnologías y herramientas usadas

- ✅ **Spring Boot 3.5.3**
- ✅ **Spring WebFlux** (programación reactiva)
- ✅ **MongoDB Atlas** como base de datos en la nube
- ✅ **Lombok** para reducir código repetitivo
- ✅ **Docker** para contenerización
- ✅ **Maven** como sistema de construcción
- ✅ **Clean Architecture** + **SOLID principles**
- ✅ **JUnit 5** para pruebas unitarias
- ✅ **Infraestructura como código** (próximamente con Terraform)

---

## 🚀 Endpoints implementados

### 📌 Franquicias

| Método | Endpoint                            | Descripción                                         |
|--------|-------------------------------------|-----------------------------------------------------|
| POST   | `/api/franchises`                  | Crear nueva franquicia (con o sin sucursales)       |
| GET    | `/api/franchises`                  | Listar todas las franquicias con su estructura      |
| PATCH  | `/api/franchises/{franchiseId}`    | Actualizar nombre de una franquicia                |
| POST   | `/api/franchises/bulk`             | Carga masiva de franquicias                        |
| DELETE | `/api/franchises/all`              | Eliminar todas las franquicias (solo para pruebas) |


### 🏢 Sucursales

| Método | Endpoint                                               | Descripción                                     |
|--------|--------------------------------------------------------|-------------------------------------------------|
| POST   | `/api/franchises/{franchiseId}/branches`              | Añadir sucursal a una franquicia               |
| GET    | `/api/franchises/{franchiseId}/branches`              | Listar sucursales de una franquicia            |
| PATCH  | `/api/franchises/{franchiseId}/branches/{branchId}`   | Actualizar nombre de una sucursal              |

### 📦 Productos

| Método | Endpoint                                                                             | Descripción                                           |
|--------|--------------------------------------------------------------------------------------|-------------------------------------------------------|
| POST   | `/api/franchises/{franchiseId}/branches/{branchId}/products`                       | Agregar productos a una sucursal                     |
| GET    | `/api/franchises/{franchiseId}/branches/{branchId}/products`                       | Listar productos de una sucursal                     |
| DELETE | `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}`           | Eliminar producto de una sucursal                    |
| PATCH  | `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}`           | Actualizar nombre de un producto                     |
| PATCH  | `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock`     | Actualizar stock de un producto                     |
| GET    | `/api/franchises/{franchiseId}/branches/top-products`                              | Obtener producto con mayor stock por sucursal

---

## 🧪 Cómo probar la API

### 1. Clona el repositorio
```bash
git clone https://github.com/dev-LiveCode/pruebaAccenture
cd pruebaAccenture
```

### 2. Configurar la conexión en `src/main/resources/application.properties`:
```properties
server.port=8080
spring.data.mongodb.uri=mongodb+srv://<usuario>:<clave>@cluster.mongodb.net/prueba-accenture-franquicia
```

> ⚠️ Por seguridad, no se comparten credenciales reales en este archivo.

### 3. Ejecutar:
```bash
./mvnw spring-boot:run
```
```Windows cmd
./mvnw spring-boot:run
```

### 4. Probar la API en Postman:  
📬 [Descargar colección Postman](./postman/Franchise API - Collection - Accenture.postman_collection.json) 

---

## 🧪 Objetos de prueba

```json
{
  "name": "Tiendas D1",
  "branches": [
    {
      "name": "Sucursal 1",
      "products": [
        { "name": "Arroz", "stock": 10 },
        { "name": "Aceite", "stock": 20 }
      ]
    }
  ]
}
```

---

## 🧪 Pruebas automatizadas

El proyecto incluye pruebas automatizadas para los siguientes endpoints usando `JUnit 5` y `WebTestClient`:

| Endpoint probado                                                        | Tipo de prueba       |
|------------------------------------------------------------------------|----------------------|
| `POST /api/franchises`                                                 | Crear franquicia     |
| `GET /api/franchises`                                                  | Obtener todas        |
| `PATCH /api/franchises/{id}`                                           | Actualizar nombre    |
| `POST /api/franchises/{id}/branches`                                   | Crear sucursal       |
| `PATCH /api/franchises/{id}/branches/{id}`                             | Actualizar sucursal  |
| `POST /api/franchises/{id}/branches/{id}/products`                     | Agregar productos    |
| `PATCH /api/franchises/{id}/branches/{id}/products/{id}`               | Actualizar producto  |
| `PATCH /api/franchises/{id}/branches/{id}/products/{id}/stock`         | Actualizar stock     |
| `GET /api/franchises/{id}/branches/top-products`                       | Mayor stock          |
| `DELETE /api/franchises/{id}/branches/{id}/products/{id}`              | Eliminar producto    |

Además, las pruebas utilizan un archivo `data.example.json` con datos precargados de ejemplo para validación estructurada.

> ✅ Las pruebas pueden ejecutarse con:
```bash
./mvnw test
```

---

## 🐳 Docker

### ✅ Opción A: Ejecutar imagen desde Docker Hub

Puedes ejecutar la API directamente desde Docker Hub sin necesidad de clonar ni compilar el proyecto:

```bash
docker pull vslivecode/franquicia-api-accenture:latest
docker run -p 8080:8080 vslivecode/franquicia-api-accenture:latest
```
Asegúrate de tener configurado el acceso a tu base de datos MongoDB.
Puedes pasar las variables de entorno directamente al contenedor o montar un archivo application.properties

### 🔧 Opción B: Construir la imagen localmente

Si deseas construir la imagen en tu equipo desde el código fuente:

# 1. Construye la imagen Docker
```bash
docker build -t franquicia-api-accenture .
```

# 2. Ejecuta el contenedor
```bash
docker run -p 8080:8080 franquicia-api-accenture
```

---

## 🧪 Pruebas unitarias

```bash
./mvnw test
```

---

## ✅ Checklist de criterios cubiertos

| Criterio                                                   | Estado     |
|------------------------------------------------------------|------------|
| Spring Boot                                                | ✅ Hecho    |
| Endpoints solicitados                                      | ✅ Hecho    |
| MongoDB Atlas                                              | ✅ Hecho    |
| Programación reactiva (WebFlux)                            | ✅ Hecho    |
| Clean Architecture                                         | ✅ Hecho    |
| Docker                                                     | ✅ Hecho |
| Infraestructura como código (Terraform)                    | 🔄 En progreso |
| Pruebas unitarias                                          | ✅ Hecho |
| Buenas prácticas (SOLID, Clean Code)                       | ✅ Hecho    |
| Control de excepciones personalizado                       | ✅ Hecho    |
| Documentación de endpoints y DTOs                          | ✅ Hecho    |

---

## 👨‍💻 Autor

Victor Vivas  
Full Stack Developer | Clean Architecture Lover  
🔗 [@dev-LiveCode](https://github.com/dev-LiveCode)

---

## 📄 Licencia

MIT License