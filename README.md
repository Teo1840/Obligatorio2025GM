# UMovie — Obligatorio 2025

Sistema de consultas sobre una base de datos de películas. Carga datos desde archivos CSV y responde 6 consultas usando estructuras de datos implementadas desde cero.

## Estructuras de datos implementadas

Todas las estructuras están en `src/main/java/um/edu/uy/tads/` y no dependen de `java.util.*`.

| Clase | Tipo |
|---|---|
| `MyList` | Lista enlazada con clave y dato por nodo |
| `MyHashImpl` | Tabla hash con encadenamiento, capacidad configurable |
| `MyOpenedHashImpl` | Tabla hash con encadenamiento y rehashing automático |
| `MyClosedHashImpl` | Tabla hash con sondeo lineal o cuadrático |
| `MyTree` | Árbol binario de búsqueda |
| `HeapImpl` | Heap binario (min o max) |

## Datasets

Archivos CSV en `src/main/resources/DATASETS v2/`:

| Archivo | Contenido |
|---|---|
| `movies_metadata.csv` | Películas con saga, géneros, productoras, países, idiomas |
| `credits.csv` | Elenco y equipo de producción por película |
| `ratings_1mm.csv` | Calificaciones de usuarios (userId, movieId, rating, timestamp) |

## Consultas disponibles

1. **Top 5 películas más calificadas por idioma** — agrupa por idioma original y muestra las 5 más votadas en cada uno.
2. **Top 10 películas con mejor calificación promedio** — ordena por promedio de ratings de usuarios.
3. **Top 5 colecciones con más ingresos** — suma ingresos de todas las películas de cada saga.
4. **Top 10 directores con mejor calificación promedio** — cruza créditos con calificaciones.
5. **Actor con más calificaciones por mes** — para cada mes del año muestra el actor cuyas películas recibieron más ratings.
6. **Usuarios con más calificaciones por género** — muestra el usuario más activo para cada género. *(Requiere que los datos de género estén poblados en Carga.)*

## Cómo ejecutar

```
./gradlew run
```

O desde el IDE: correr `Main.java`. Al iniciar seleccionar **1. Carga de Datos** antes de ejecutar cualquier consulta.

## Dependencias

- [OpenCSV 5.11.2](https://mvnrepository.com/artifact/com.opencsv/opencsv) — lectura de archivos CSV
- [Gson 2.13.1](https://mvnrepository.com/artifact/com.google.code.gson/gson) — parseo de campos JSON embebidos en el CSV

## Integrantes

<!-- Agregar nombres del equipo -->
