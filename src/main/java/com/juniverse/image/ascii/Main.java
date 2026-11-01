package com.juniverse.image.ascii;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Convierte {@code image.jpeg} (de resources) en arte ASCII de 100 columnas y lo guarda en {@code ascii-art.txt}.
 *
 * @author linux
 */
public class Main {
    private static final Logger LOGGER = AppLogger.get(Main.class);


    /**
     * Punto de entrada.
     * @param args no se usan
     */
    public static void main(String[] args) {
        try {
            ImageToAscii img = new ImageToAscii();
            String fileName = "image.jpeg";
            int width;
            int height;

            BufferedImage image = img.loadImage(fileName);
            width = 100;
            // Los caracteres son más altos que anchos (~2:1): se reduce la altura a la mitad para no estirar la imagen
            height = Math.max(1, (image.getHeight() * width) / (2 * image.getWidth()));

            image = img.resizeImage(image, width, height);
            String asciiArt = img.convertToAscii(image);
                      
            img.writeConsole(asciiArt);
            img.writeFile(asciiArt);

        } catch (IOException | URISyntaxException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
