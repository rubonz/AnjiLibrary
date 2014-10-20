package com.darkempire.math.struct.function.doublesfunction;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.math.struct.IDeepcopy;
import com.darkempire.math.struct.LinearModifable;
import com.darkempire.math.struct.Modifable;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.FDoublesDouble;
import com.darkempire.math.struct.function.doublefunction.DoubleFunctions;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
public abstract class DoublesFunction implements FDoublesDouble, Modifable<DoublesFunction>, LinearModifable<DoublesFunction>, IDeepcopy<DoublesFunction> {

    @Override
    public abstract double calc(double... x);

    @Override
    public DoublesFunction multiply(DoublesFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoublesFunction divide(DoublesFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoublesFunction inverse() {
        return null;//TODO:обробити
    }

    @Override
    public DoublesFunction add(DoublesFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoublesFunction subtract(DoublesFunction doubleFunction) {
        return null;//TODO:обробити
    }

    @Override
    public DoublesFunction negate() {
        return null;//TODO:обробити
    }

    public DoublesFunction derivative() {
        return null;//TODO:обробити
    }

    public DoublesFunction integrate() {
        return null;//TODO:обробити
    }

    public DoublesFunction prod(double lambda) {
        return deepcopy().iprod(lambda);
    }

    @AnjiExperimental
    public abstract DoublesFunction iprod(double lambda);

    @Override
    public abstract String toString();
}
