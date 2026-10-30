# Imagen a arte ASCII

Convierte una imagen en arte ASCII, sustituyendo cada píxel por un carácter según su brillo.

## Características

- Reduce la imagen a 100 columnas conservando la proporción (corrige que los caracteres son más altos que anchos).
- Calcula la luminosidad con la fórmula de la Rec. 709.
- Muestra el resultado en la consola y lo guarda en `ascii-art.txt`.
- La imagen se carga desde `src/main/resources` (también funciona dentro de un JAR).

## Requisitos

- JDK 21 y Maven 3.9 o superior.

## Compilar y ejecutar

```bash
mvn compile exec:java
```

## Notas

- Para usar otra imagen, colócala en `src/main/resources` y cambia `fileName` en `Main.java`.

## JAR ejecutable (fat jar)

```bash
mvn clean package
java -jar target/mvn-image-ascii-1.0.0-SNAPSHOT-launcher.jar
```

El archivo `-launcher.jar` incluye todas las dependencias.

## Licencia

Código de ejemplo de uso libre con fines educativos.
