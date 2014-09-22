package com.darkempire.math.struct.fractal.lsystem;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class StochasticCharType implements ICharType {
    private class ProbabilisticCharType {
        private ILSystem.CharType charType;
        private double p;

        public ProbabilisticCharType(ILSystem.CharType charType, double p) {
            this.charType = charType;
            this.p = p;
        }

        public ILSystem.CharType getCharType() {
            return charType;
        }

        public double getP() {
            return p;
        }
    }

    private double one;
    private List<ProbabilisticCharType> charTypes;

    public StochasticCharType() {
        this.one = 0;
        this.charTypes = new ArrayList<>();
    }

    public void addRule(ILSystem.CharType charType, double p) {
        charTypes.add(new ProbabilisticCharType(charType, p));
        one += p;
    }

    @Override
    public ILSystem.CharType getCharType() {
        double p = random() * one;
        ILSystem.CharType result = null;
        for (ProbabilisticCharType charType : charTypes) {
            if (p < charType.getP()) {
                result = charType.getCharType();
                break;
            } else {
                p -= charType.getP();
            }
        }
        return result;
    }
}
