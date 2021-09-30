package com.ing.demospringmail.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    private ImageUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void textToImage(String name) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = image.createGraphics();

        Font pixelMPlus = null;
        try {
            pixelMPlus = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/INGMe.otf")).deriveFont(33f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/INGMe.otf")));

        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }

        String text = "Değerli Müşterimiz\n" + name + "\nAramıza Hoş Geldiniz!";
        String hosGeldiniz = "\nAramıza Hoş Geldiniz!";

        graphics2d.setFont(pixelMPlus);
        FontMetrics fontmetrics = graphics2d.getFontMetrics();
        int width;
        if (name.length() > hosGeldiniz.length()) {
            width = fontmetrics.stringWidth(name);
        } else {
            width = fontmetrics.stringWidth(hosGeldiniz);
        }
        int height = fontmetrics.getHeight();
        height = height * 4;
        graphics2d.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2d = image.createGraphics();

        graphics2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2d.setFont(pixelMPlus);
        graphics2d.setColor(new Color(51, 51, 51));

        graphics2d = drawStringOver(graphics2d, text, 0, 0, width, height);
        graphics2d.dispose();
        try {
            ImageIO.write(image, "png", new File("src/main/resources/images/custom/" + name + ".jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static Graphics2D drawStringOver(Graphics2D g, String text, int x, int y, int width, int height) {
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, width, height);

        for (String line : text.split("\n")) {
            y = y + g.getFontMetrics().getHeight();
            g.drawString(line, x, y);
        }
        return g;
    }
}
