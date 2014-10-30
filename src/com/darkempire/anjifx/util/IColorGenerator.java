package com.darkempire.anjifx.util;

import javafx.scene.paint.Color;

/**
 * Created by siredvin on 29.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
public interface IColorGenerator {
    public static IColorGenerator getGrayColorGenerator() {
        return AnjiFXColorUtil::randomGray;
    }

    public static IColorGenerator getColorGenerator() {
        return AnjiFXColorUtil::randomColor;
    }

    public Color getNextColor();

    public static class GradientColorGenerator implements IColorGenerator {
        private Color colorStart;
        private Color colorEnd;
        private double index;
        private double step;

        public GradientColorGenerator(Color colorStart, Color colorEnd, double step) {
            this.colorStart = colorStart;
            this.colorEnd = colorEnd;
            this.index = 0;
            this.step = step;
        }

        @Override
        public Color getNextColor() {
            Color c = AnjiFXColorUtil.mixColor(colorEnd, colorStart, index, Math.abs(1 - index));
            index += step;
            return c;
        }
    }

    public static class RGBIncrementGenerator implements IColorGenerator {
        private Color color;
        private int index;
        private double rInc, gInc, bInc;

        public RGBIncrementGenerator(Color color, double rInc, double gInc, double bInc) {
            this.color = color;
            this.rInc = rInc;
            this.gInc = gInc;
            this.bInc = bInc;
            this.index = 0;
        }

        @Override
        public Color getNextColor() {
            double r = color.getRed() + rInc * index;
            double b = color.getBlue() + bInc * index;
            double g = color.getGreen() + gInc * index;
            index++;
            r = r - Math.floor(r);
            b = b - Math.floor(b);
            g = g - Math.floor(g);
            return Color.color(r, g, b);
        }
    }

    public static class GrayIncrementGenerator implements IColorGenerator {
        private double value;
        private double inc;

        public GrayIncrementGenerator(double value, double inc) {
            this.value = value;
            this.inc = inc;
        }

        @Override
        public Color getNextColor() {
            Color c = Color.gray(value);
            value += inc;
            if (value > 1) {
                value = value - Math.floor(value);
            }
            return c;
        }
    }
}
