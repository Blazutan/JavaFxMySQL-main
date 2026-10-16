# Gestión de tarjetas

Aplicación de escritorio desarrollada en Java y JavaFX para crear y consultar
tarjetas de débito y crédito. Permite asignar a cada tarjeta uno de estos
estados: `ACTIVA`, `BLOQUEADA` o `CANCELADA`, y actualizarlo después de
consultar la tarjeta.

## Tecnologías

- Java 11
- JavaFX 13 (FXML y Controls)
- MySQL
- Maven
- JUnit 5

## Requisitos

- JDK 11 o superior
- Maven 3.6 o superior
- MySQL 8 o compatible

## Configuración de la base de datos

1. Desde la raíz del proyecto, ejecuta [`ScriptDB.sql`](./ScriptDB.sql) en
   MySQL. El script crea la base de datos `examen2_soto`, sus tablas y datos de
   ejemplo. Para una base ya creada con el campo `estado` booleano, también
   convierte los valores existentes (`1` y `0`) a `ACTIVA` y `BLOQUEADA`.
2. Crea un archivo `.env` en la raíz del proyecto o en `examen2/` con la
   configuración de tu entorno:

   ```dotenv
   DB_URL=jdbc:mysql://localhost:3306/examen2_soto
   DB_USER=tu_usuario
   DB_PASSWORD=tu_contrasena
   ```

   Sustituye los valores de usuario y contraseña por los de tu servidor MySQL.
   No compartas ni subas este archivo con credenciales reales.

La conexión carga esas tres variables y usa el driver MySQL Connector/J
declarado en Maven.

## Ejecutar la aplicación

Desde la carpeta `examen2/`:

```shell
mvn clean javafx:run
```

## Ejecutar las pruebas

Desde `examen2/`:

```shell
mvn test
```

Las pruebas unitarias cubren el modelo de tarjeta. Las pruebas de integración
requieren que MySQL esté configurado y que la base de datos tenga los titulares
de ejemplo definidos en `ScriptDB.sql`.

## Uso

- **Consultar:** escribe el número de una tarjeta y selecciona **Consultar**.
  Se muestran los datos y el estado actual.
- **Actualizar estado:** después de consultar una tarjeta, selecciona el
  estado deseado y pulsa **Actualizar estado**.
- **Crear:** introduce el número, fecha de expiración y titular, selecciona el
  estado inicial y el tipo de tarjeta, y pulsa **Crear tarjeta**.

## Estructura del proyecto

```text
.
├── ScriptDB.sql
├── README.md
└── examen2/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/co/edu/poli/examen2_Soto/
        │   │   ├── controlador/   # Controlador JavaFX
        │   │   ├── modelo/        # Tarjetas, titulares y estados
        │   │   ├── servicios/     # DAO y conexión MySQL
        │   │   └── vista/         # Inicio de la aplicación
        │   └── resources/         # Vistas FXML
        └── test/
            └── java/              # Pruebas unitarias y de integración
```
