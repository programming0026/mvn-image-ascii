package com.juniverse.image.ascii;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 * Convierte una imagen en arte ASCII: la reduce de tamaño y sustituye cada píxel por un carácter
 * según su brillo (de "@" para lo más oscuro a espacio para lo más claro).
 *
 * @author linux
 */
public class ImageToAscii {

    private static final String ASCII_CHARS = "@%#*+=-:. ";

    /**
     * Carga una imagen desde el classpath.
     * @param fileName nombre del recurso
     * @return la imagen
     * @throws IOException si no existe o su formato no está soportado
     * @throws URISyntaxException no se produce; se mantiene por compatibilidad
     */
    public BufferedImage loadImage(String fileName) throws URISyntaxException, IOException {
        // Se lee como flujo para que funcione también dentro del JAR (new File(uri) falla con recursos empaquetados)
        try (InputStream in = ImageToAscii.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new FileNotFoundException("No se encontró el recurso " + fileName);
            }
            BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new IOException("Formato de imagen no soportado: " + fileName);
            }
            return image;
        }
    }

    /**
     * Cambia el tamaño de la imagen.
     * @param originalImage imagen original
     * @param width nuevo ancho en píxeles
     * @param height nuevo alto en píxeles
     */
    public BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        try {
            g.drawImage(originalImage, 0, 0, width, height, null);
        } finally {
            g.dispose(); // libera los recursos gráficos
        }
        return resizedImage;
    }

    /**
     * Convierte cada píxel en un carácter según su luminosidad (fórmula Rec. 709).
     * @param image imagen ya reducida
     * @return texto con una línea por fila
     */
    public String convertToAscii(BufferedImage image) {
        StringBuilder asciiArt = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                double brightness = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
                int index = (int) ((brightness * (ASCII_CHARS.length() - 1)) / 255);
                asciiArt.append(ASCII_CHARS.charAt(index));
            }
            asciiArt.append("\n");
        }
        return asciiArt.toString();
    }

    /**
     * Imprime el arte ASCII en la consola.
     * @param ascii texto a imprimir
     */
    public void writeConsole(String ascii) {
        System.out.println(ascii);
    }

    /**
     * Guarda el arte ASCII en {@code ascii-art.txt}.
     * @param ascii texto a guardar
     * @throws IOException si no se puede escribir el archivo
     */
    public void writeFile(String ascii) throws IOException {
        try (FileWriter writer = new FileWriter("ascii-art.txt")) {
            writer.write(ascii);
        }
    }
}
