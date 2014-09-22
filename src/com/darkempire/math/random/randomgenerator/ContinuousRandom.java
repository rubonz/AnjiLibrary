package com.darkempire.math.random.randomgenerator;

import com.darkempire.anji.annotation.AnjiUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 05.11.13
 * Time: 20:45
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class ContinuousRandom {
    private static Random random = new Random();

    private ContinuousRandom() {
    }

    public static void setRandom(Random random) {
        ContinuousRandom.random = random;
    }

    /**
     * @return Випадкову значення для випадкової величини, що розподілена за законом Cauchy(0,1). Примітка: Cauchy(l,|k|) = kCauchy(0,1)+l
     */
    public static double cauchyRand() {
        return Math.tan(Math.PI * (random.nextDouble() - 0.5));
    }

    /**
     * @return Випадкову величину, розподілену за нормальний законом
     */
    public static double normalRand() {
        return Math.sqrt(-2 * Math.log(random.nextDouble())) * Math.cos(Math.random());
    }
}
