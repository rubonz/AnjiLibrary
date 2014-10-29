package com.darkempire.anjifx.util;

import com.darkempire.anji.annotation.AnjiUtil;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by siredvin on 16.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class AnjiFXColorUtil {
    private static Random rand = new Random();

    private AnjiFXColorUtil() {
    }

    public static Color randomColor() {
        return Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    public static Color randomGray() {
        return Color.gray(rand.nextDouble(), rand.nextDouble());
    }

    public static Color mixColor(Color c1, Color c2, double w1, double w2) {
        double sum = w1 + w2;
        return new Color((c1.getRed() * w1 + c2.getRed() * w2) / sum, (c1.getGreen() * w1 + c2.getGreen() * w2) / sum, (c1.getBlue() * w1 + c2.getBlue() * w2) / sum, 1);
    }
}
