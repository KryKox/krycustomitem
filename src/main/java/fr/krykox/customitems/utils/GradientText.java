package fr.krykox.customitems.utils;

import java.awt.Color;

/**
 * The type Gradient text.
 */
public class GradientText {

    /**
     * Generate hex gradient string.
     *
     * @param text  the text
     * @param start the start
     * @param end   the end
     * @return the string
     */
    public static String generateHexGradient(String text, Color start, Color end) {
        StringBuilder builder = new StringBuilder();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            float ratio = (length == 1) ? 0 : (float) i / (length - 1);

            int red = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
            int green = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int blue = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

            String hex = String.format("%02x%02x%02x", red, green, blue);
            builder.append("§x");
            for (char c : hex.toCharArray()) {
                builder.append('§').append(c);
            }
            builder.append(text.charAt(i));
        }

        return builder.toString();
    }
}
