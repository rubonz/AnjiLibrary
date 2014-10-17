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
}
