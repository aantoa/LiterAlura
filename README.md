# 📚 LiterAlura - Challenge Java 📚

[![Licencia MIT](https://img.shields.io/badge/Licencia-MIT-green.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.1-blue?logo=apachemaven)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)


LiterAlura es una aplicación de consola en Java + Spring Boot que te permite consultar, almacenar y explorar información de libros y autores usando la API pública de Gutendex, y persistir datos en PostgreSQL. Desarrollado como parte del programa Oracle ONE - Alura Latam.

---
## 🚀 Funcionalidades principales

- Buscar libros por título y guardarlos con su autor en la base de datos
- Listar todos los libros registrados
- Filtrar libros por idioma
- Listar autores guardados
- Listar autores vivos en determinado año (consulta optimizada en BD)
- Estadísticas de libros por idioma y descargas (Streams y consultas JPA)
- Top 10 libros más descargados
- Buscar autores por nombre
- Filtrar autores por año de nacimiento o fallecimiento

---

## 💡 ¿Cómo funciona?

El usuario interactúa con un menú amigable en consola, pudiendo buscar libros, consultar autores, ver estadísticas y filtrar resultados.

### Menú real en consola:
![Menú de LiterAlura](img.png)

---

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- PostgreSQL
- Maven
- Jackson (JSON)
- Java Streams y Lambdas

---

## 🗂️ Estructura del proyecto

```text
src/main/java/com/literalura/
├── dto/
│     ├── AutorDTO.java
│     └── LibroDTO.java
├── model/
│     ├── Autor.java
│     ├── Libro.java
│     ├── DatosAutor.java
│     ├── DatosLibro.java
│     └── DatosCatalogoLibros.java
├── repository/
│     ├── AutorRepository.java
│     └── LibroRepository.java
├── service/
│     ├── AutorService.java
│     ├── LibroService.java
│     ├── ConsumoAPI.java
│     ├── ConvierteDatos.java
│     └── IConvierteDatos.java
├── principal/
│     └── Principal.java
└── LiterAluraApplication.java
```
---

## ⚙️ Instalación y uso

1. **Clona el repositorio:**
```sh
git clone https://github.com/aantoa/LiterAlura.git
cd LiterAlura
```
2. **Crea la base de datos PostgreSQL (ejemplo):**
```
CREATE DATABASE alura_challenge;
```
3. **Configura tus credenciales de base de datos:**
   Abre src/main/resources/application.properties:
```
spring.application.name=LiterAlura

spring.datasource.url=jdbc:postgresql://${DB_HOST_ALURA}/${DB_NAME_CHALLENGE_LITERALURA}
spring.datasource.username=${DB_USER_ALURA}
spring.datasource.password=${DB_PASSWORD_ALURA}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=ERROR
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=ERROR

spring.jpa.properties.hibernate.format_sql=true
```
4. **Define las variables de entorno necesarias antes de arrancar la aplicación:**

* En Linux/Mac
```
export DB_HOST_ALURA=localhost:5432
export DB_NAME_CHALLENGE_LITERALURA=alura_challenge
export DB_USER_ALURA=tu_usuario
export DB_PASSWORD_ALURA=tu_password
```
* En Windows CMD:
```
set DB_HOST_ALURA=localhost:5432
set DB_NAME_CHALLENGE_LITERALURA=alura_challenge
set DB_USER_ALURA=tu_usuario
set DB_PASSWORD_ALURA=tu_password
```
5. **Compila y ejecuta el proyecto:**
```sh
mvn spring-boot:run
```
---

## 👨‍💻 Ejemplo de uso
* Buscar libro:
Ingresa el título y el sistema consultará la API, guardará el libro y el autor (si no existen), y mostrará el resultado.

* Estadísticas:
Visualiza cuántos libros tienes por idioma o el top 10 de descargas.

* Filtrar autores:
Encuentra autores vivos en un año específico, por nombre, año de nacimiento o fallecimiento.

---

## 💬 Sobre el desarrollo
- Este proyecto fue desarrollado como parte del Challenge de Back-End en el programa Oracle Next Education - Alura Latam.
- El objetivo fue practicar integración de APIs, persistencia de datos, buenas prácticas en Java y Spring, y la entrega de una experiencia sólida al usuario.

---

## ✨ Créditos
* Autor: [Amalia Anto Alzamora](https://github.com/aantoa)
* API utilizada: [Gutendex](https://gutendex.com/)
* Inspirado por [Alura Latam](https://www.aluracursos.com/)

---

## 📜 Licencia
* Este proyecto está bajo la Licencia MIT (`MIT`) 
