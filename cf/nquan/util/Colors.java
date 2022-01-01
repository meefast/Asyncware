package cf.nquan.util;

import java.awt.Color;

public class Colors {
    public static int Astolfo(int var2, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + (long) (var2 * 100)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }

    public static int RGB(float sat, float br) {
        float rgb = (System.currentTimeMillis() % 5000)/5000f;
        int color = Color.HSBtoRGB(rgb, sat, br);
        return color;
    }

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

    public static int RGB() {
        float rgb = (System.currentTimeMillis() % 5000)/5000f;
        int color = Color.HSBtoRGB(rgb, 1.0f, 1);
        return color;
    }
    public static int RGBX(float seconds, float saturation, float brightness, long index) {
        float rgb = (System.currentTimeMillis() + index) % (int)(seconds * 5000) /  (float)(seconds * 5000);
        int color = Color.HSBtoRGB(rgb, 0.5f, 1);
        return color;
    }


    public static int getFade(float seconds, float saturation, float brightness) {
        float hue = (float)(System.currentTimeMillis() % (int)(seconds * 10000.0F)) / seconds * 10000.0F;
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getFade2(float seconds, float saturation, float brightness, long index) {
        float hue = (float)((System.currentTimeMillis() + index) % (int)(seconds * 1000)) / (float)(seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }
    public static int fadeBetween(int color1, int color2, float offset) {
        if (offset > 1)
            offset = 1 - offset % 1;

        double invert = 1 - offset;
        int r = (int) ((color1 >> 16 & 0xFF) * invert +
                (color2 >> 16 & 0xFF) * offset);
        int g = (int) ((color1 >> 8 & 0xFF) * invert +
                (color2 >> 8 & 0xFF) * offset);
        int b = (int) ((color1 & 0xFF) * invert +
                (color2 & 0xFF) * offset);
        int a = (int) ((color1 >> 24 & 0xFF) * invert +
                (color2 >> 24 & 0xFF) * offset);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    public static int darker(int color, float factor) {
        int r = (int) ((color >> 16 & 0xFF) * factor);
        int g = (int) ((color >> 8 & 0xFF) * factor);
        int b = (int) ((color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;

        return ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF) |
                ((a & 0xFF) << 24);
    }
}
