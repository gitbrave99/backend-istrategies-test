# Backend Test Fullstack Developer
Proyecto Fullstack para La empresa Matinsa S.A. de C.V. requiere de una aplicación en la cual pueda registrar los productos que conforman su inventario, registrar las ordenes de producción que realizan, y conocer en todo momento con que cantidad de productos cuentan en su inventario.

## Stack utilizado
- Spring Data JPA
- Java 21
- JWt 0.9.1
- Validation
- Mapstruct 1.5.4.Final
- JasperReports 6
- Maven

## Correr aplicación
- Crear base de datos
- Cargar dependencias de Maven
- Configuración de java 21
- Modificar archivo `applicacion.properties` y agregar el usuario y contraseña para conección con la base de datos.
- Correr clase principlal `FullstackApplication.java`
- Url `http://localhost:8098/api/v1`
- Url para el login `http://localhost:8098/api/v1/login`. Hay 2 usuarios de prueba, para el módulo de bodega el **usuario:** *bodega* y para producción el **usuario:** *produccion* con la **contraseña:** *123* .

Si quiere crear más usuarios el endpoint `/usuario/create` lo hace enviándole el siguiente json
```json
{
    "username": "username",
    "password": "123",
    "idUsuarioTipo": 1
}
```
El **idUsuarioTipo** tiene 2 tipos
1. Bodega
2. Proucción

