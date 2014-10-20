package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.math.struct.IDeepcopy;
import com.darkempire.math.struct.LinearModifable;
import com.darkempire.math.struct.Modifable;
import com.darkempire.math.struct.function.FDoubleDouble;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
public abstract class DoubleFunction implements FDoubleDouble, Modifable<DoubleFunction>, LinearModifable<DoubleFunction>, IDeepcopy<DoubleFunction> {

    @Override
    public abstract double calc(double x);

    @Override
    public DoubleFunction multiply(DoubleFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleFunction divide(DoubleFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleFunction inverse() {
        return null;//TODO:обробити
    }

    @Override
    public DoubleFunction add(DoubleFunction doubleFunction) {
        return DoubleFunctions.sum(this, doubleFunction);
    }

    @Override
    public DoubleFunction subtract(DoubleFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleFunction negate() {
        return null;//TODO:обробити
    }

    public DoubleFunction derivative() {
        return null;//TODO:обробити
    }

    public DoubleFunction integrate() {
        return null;//TODO:обробити
    }

    public DoubleFunction prod(double lambda) {
        return deepcopy().iprod(lambda);
    }

    @AnjiExperimental
    public abstract DoubleFunction iprod(double lambda);

    @Override
    public abstract String toString();
}
