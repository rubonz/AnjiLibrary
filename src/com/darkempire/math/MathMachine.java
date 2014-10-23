package com.darkempire.math;

import com.darkempire.anji.annotation.AnjiUtil;

import java.text.NumberFormat;

/**
 * Create in 8:50
 * Created by siredvin on 20.12.13.
 */
@AnjiUtil
public final class MathMachine {
    public static final double MACHINE_EPS = 1E-16;
    public static final double MACHINE_HALF_EPS = 1E-8;
    public static final long MACHINE_REVERSE_EPS = 10000000000000000l;
    public static final int MACHINE_HALF_REVERSE_EPS = 100000000;
    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    static {
        numberFormat.setMaximumFractionDigits(3);
    }

    public static double MACHINE_PRACTICAL_EPS = 1E-14;

    private MathMachine() {
    }
}
