
# Franquicia API 🏬

API para la gestión de franquicias, sucursales y productos, desarrollada como prueba técnica para el rol de desarrollador Backend para Accenture.

---

## 🧱 Arquitectura del Proyecto

Este proyecto implementa una arquitectura basada en **Clean Architecture**, principios **SOLID**, **Clean Code** y programación **reactiva** con Spring WebFlux.  
Está diseñado para mantener un alto nivel de mantenibilidad, desacoplamiento y escalabilidad.

```
src/main/java/com/victorvivas/pruebaacenture/accenture_granquicia_api
│
├── application/          → Casos de uso y orquestación de lógica
│
├── domain/               → Lógica de negocio
│   ├── model/            → Entidades del dominio: Franchise, Branch, Product
│   └── repository/       → Interfaces (puertos de salida)
│
├── infrastructure/       → Adaptadores secundarios como MongoDB (puertos de entrada)
│   └── persistence/      → Implementaciones de repositorios reactivos
│
└── entrypoints/          → Adaptadores primarios
    └── rest/             → Controladores expuestos como API REST
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

## 🧪 Funcionalidades Implementadas

1. ➕ Crear una nueva **franquicia**
2. ➕ Agregar una **sucursal** a una franquicia existente
3. ➕ Agregar un **producto** a una sucursal
4. ❌ Eliminar un producto de una sucursal
5. 🔁 Modificar el **stock** de un producto
6. 📈 Obtener el **producto con mayor stock** por sucursal para una franquicia
7. 📝 Actualizar el nombre de la **franquicia**
8. 📝 Actualizar el nombre de una **sucursal**
9. 📝 Actualizar el nombre de un **producto**

---

## 🚀 Cómo ejecutar la aplicación

### 1. Clona el repositorio

```bash
git clone https://github.com/dev-LiveCode/pruebaAccenture
cd pruebaAccenture
```

### 2. Configura la conexión a la base de datos

Crea un archivo `src/main/resources/application.properties` con la siguiente configuración:

```properties
server.port=8080
spring.data.mongodb.uri=mongodb+srv://<usuario>:<clave>@cluster0.mongodb.net/prueba-accenture-franquicia?retryWrites=true&w=majority
```

> ⚠️ Por seguridad, no compartas credenciales reales en este archivo si vas a subir el proyecto.

### 3. Ejecuta el proyecto

```bash
./mvnw spring-boot:run
```

O en Windows CMD:

```cmd
mvnw spring-boot:run
```

### 4. Prueba los endpoints

Una vez en ejecución, accede a:  
📍 `http://localhost:8080/api/franquicias`

Puedes usar herramientas como **Postman** o **cURL** para probar los endpoints.

---

## 📦 Docker

Puedes construir la imagen con:

```bash
docker build -t franquicia-api .
docker run -p 8080:8080 franquicia-api
```

(El archivo `Dockerfile` está incluido en el proyecto.)

---

## 🧪 Pruebas unitarias

```bash
./mvnw test
```

Se incluyen pruebas para la lógica de negocio y controladores.

---

## 📁 Estructura sugerida para Infraestructura como Código (IaC)

El proyecto está preparado para usar infraestructura como código con Terraform. La estructura será:

```
infra/
├── main.tf
├── variables.tf
└── outputs.tf
```

(Próxima implementación)

---

## 🧑‍💻 Autor

Victor Vivas  
Full Stack Developer 🚀🪐 | Clean architecture lover  
GitHub: [@dev-LiveCode](https://github.com/dev-LiveCode)

---

## 📄 Licencia

MIT License

---

### ✅ Checklist de criterios cubiertos

| Criterio                                                   | Estado     |
|------------------------------------------------------------|------------|
| Spring Boot                                                | ✅ Hecho    |
| Endpoints solicitados                                      | 🔄 En progreso    |
| MongoDB (en la nube)                                       | ✅ Hecho    |
| Programación reactiva                                      | 🔄 En progreso    |
| Clean Architecture                                         | ✅ Hecho    |
| Docker                                                     | 🔄 En progreso    |
| Infraestructura como código                                | 🔄 En progreso |
| Pruebas unitarias                                          | 🔄 En progreso    |
| Documentación y despliegue local                           | 🔄 En progreso    |
| Buenas prácticas de código (SOLID, Clean Code)             | 🔄 En progreso    |
