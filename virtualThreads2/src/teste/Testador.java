package teste;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Testador {
    public static void main(String[] args) {
        String imagePath = "images/foto2.jpeg";

        File inputFile = new File(imagePath);
        BufferedImage image;
        try {
            image = ImageIO.read(inputFile);

            int width = image.getWidth();
            int height = image.getHeight();

            int brightPixelsCount = 0;

            int threshold = 200; // Você pode ajustar esse valor conforme necessário

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);

                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    if (red > threshold && green > threshold && blue > threshold) {
                        brightPixelsCount++;
                    }
                }
            }

            double totalPixels = width * height;
            double percentageBrightPixels = Math.round((brightPixelsCount / totalPixels) * 100);

            System.out.println("Percentual de pixels claros na imagem: " + percentageBrightPixels + "%");

        } catch (IOException e) {
            System.out.println("Erro ao ler a imagem: " + e.getMessage());
        }
    }
}
