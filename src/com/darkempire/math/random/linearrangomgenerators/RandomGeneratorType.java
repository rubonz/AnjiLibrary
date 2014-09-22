package com.darkempire.math.random.linearrangomgenerators;

import java.util.Random;


public enum RandomGeneratorType {
    mersennetwister, mersennetwisterfast, linearcogurent;

    public static Random getRandom(RandomGeneratorType t) {
        switch (t) {
            case mersennetwister:
                return new MersenneTwisterGenerator();
            case mersennetwisterfast:
                return new MersenneTwisterFastGenerator();
            case linearcogurent:
                return new Random();
        }
        return null;
    }

    public static Random getRandom(RandomGeneratorType t, long seed) {
        switch (t) {
            case mersennetwister:
                return new MersenneTwisterGenerator(seed);
            case mersennetwisterfast:
                return new MersenneTwisterFastGenerator(seed);
            case linearcogurent:
                return new Random(seed);
        }
        return null;
    }
}
