package com.darkempire.math.random.randomgenerator;

import java.util.Random;

/**
 * Created by Сергій on 17.10.2014.
 */
public class DoubleRandom {
    private Random random;

    public DoubleRandom(Random random) {
        this.random = random;
    }

    //todo: safety
    public double getRandomNumber(double min, double max){
        double x  = random.nextDouble();

        return x * (max - min) + min;
    }
}
