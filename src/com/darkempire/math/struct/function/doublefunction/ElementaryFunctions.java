package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;

import static com.darkempire.math.utils.FormatUtils.appendNumber;

/**
 * Created by siredvin on 27.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class ElementaryFunctions {
    private ElementaryFunctions() {
    }

    public static DoubleFunction exp() {
        return new ExponentialFunction();
    }

    public static DoubleFunction ln() {
        return new NaturalLogarithm();
    }

    public static DoubleFunction sin() {
        return new Sin();
    }

    public static DoubleFunction cos() {
        return new Cos();
    }


    //region Функції

    /**
     * Реалізує фукнцію експоненти у вигляді:
     * f(x) = a * e^(b*x + c) + d
     */
    public static class ExponentialFunction extends DoubleFunction {

        /**
         * Змінна, що відповідає a
         */
        private double coef;
        /**
         * Змінна, що відповідає b
         */
        private double powerCoef;
        /**
         * Змінна, що відповідає d
         */
        private double shift;
        /**
         * Змінна, що відповідає с
         */
        private double powerShift;

        public ExponentialFunction(double coef, double powerCoef, double shift, double powerShift) {
            this.coef = coef;
            this.powerCoef = powerCoef;
            this.shift = shift;
            this.powerShift = powerShift;
        }

        public ExponentialFunction() {
            coef = powerCoef = 1;
            shift = powerShift = 0;
        }

        @Override
        public double calc(double x) {
            return coef * Math.exp(powerCoef * x + powerShift) + shift;
        }

        @Override
        public DoubleFunction prod(double lambda) {
            return new ExponentialFunction(coef * lambda, powerCoef, shift * lambda, powerShift);
        }

        @Override
        public DoubleFunction iprod(double lambda) {
            coef *= lambda;
            shift *= lambda;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(MathMachine.numberFormat.format(coef))
                    .append("e^{").append(MathMachine.numberFormat.format(powerCoef))
                    .append("*x");
            appendNumber(powerShift, builder);
            builder.append('}');
            appendNumber(shift, builder);
            return builder.toString();
        }

        @Override
        public DoubleFunction deepcopy() {
            return new ExponentialFunction(coef, powerCoef, shift, powerShift);
        }
    }

    /**
     * Реалізує натуральний логарифм у вигляді:
     * f(x) = a ln(b*x + c) + d
     */
    public static class NaturalLogarithm extends DoubleFunction {
        /**
         * Змінна, що відповідає a
         */
        private double coef;
        /**
         * Змінна, що відповідає b
         */
        private double lnCoef;
        /**
         * Змінна, що відповідає d
         */
        private double shift;
        /**
         * Змінна, що відповідає с
         */
        private double lnShift;

        public NaturalLogarithm(double coef, double lnCoef, double shift, double lnShift) {
            this.coef = coef;
            this.lnCoef = lnCoef;
            this.shift = shift;
            this.lnShift = lnShift;
        }

        public NaturalLogarithm() {
            coef = lnCoef = 1;
            shift = lnShift = 0;
        }

        @Override
        public double calc(double x) {
            return coef * Math.log(lnCoef * x + lnShift) + shift;
        }

        @Override
        public DoubleFunction prod(double lambda) {
            return new NaturalLogarithm(coef * lambda, lnCoef, shift * lambda, lnShift);
        }

        @Override
        public DoubleFunction iprod(double lambda) {
            coef *= lambda;
            shift *= lambda;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(MathMachine.numberFormat.format(coef))
                    .append("\\ln\\left(").append(MathMachine.numberFormat.format(lnCoef))
                    .append("*x");
            appendNumber(lnShift, builder);
            builder.append("\\right)");
            appendNumber(shift, builder);
            return builder.toString();
        }

        @Override
        public DoubleFunction deepcopy() {
            return new NaturalLogarithm(coef, lnCoef, shift, lnShift);
        }
    }

    /**
     * Реалізує натуральний логарифм у вигляді:
     * f(x) = a cos(b*x + c) + d
     */
    public static class Cos extends DoubleFunction {
        /**
         * Змінна, що відповідає a
         */
        private double coef;
        /**
         * Змінна, що відповідає b
         */
        private double lnCoef;
        /**
         * Змінна, що відповідає d
         */
        private double shift;
        /**
         * Змінна, що відповідає с
         */
        private double lnShift;

        public Cos(double coef, double lnCoef, double shift, double lnShift) {
            this.coef = coef;
            this.lnCoef = lnCoef;
            this.shift = shift;
            this.lnShift = lnShift;
        }

        public Cos() {
            coef = lnCoef = 1;
            shift = lnShift = 0;
        }

        @Override
        public double calc(double x) {
            return coef * Math.cos(lnCoef * x + lnShift) + shift;
        }

        @Override
        public DoubleFunction prod(double lambda) {
            return new Cos(coef * lambda, lnCoef, shift * lambda, lnShift);
        }

        @Override
        public DoubleFunction iprod(double lambda) {
            coef *= lambda;
            shift *= lambda;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(MathMachine.numberFormat.format(coef))
                    .append("\\cos\\left(").append(MathMachine.numberFormat.format(lnCoef))
                    .append("*x");
            appendNumber(lnShift, builder);
            builder.append("\\right)");
            appendNumber(shift, builder);
            return builder.toString();
        }

        @Override
        public DoubleFunction deepcopy() {
            return new Cos(coef, lnCoef, shift, lnShift);
        }
    }

    /**
     * Реалізує натуральний логарифм у вигляді:
     * f(x) = a sin(b*x + c) + d
     */
    public static class Sin extends DoubleFunction {
        /**
         * Змінна, що відповідає a
         */
        private double coef;
        /**
         * Змінна, що відповідає b
         */
        private double lnCoef;
        /**
         * Змінна, що відповідає d
         */
        private double shift;
        /**
         * Змінна, що відповідає с
         */
        private double lnShift;

        public Sin(double coef, double lnCoef, double shift, double lnShift) {
            this.coef = coef;
            this.lnCoef = lnCoef;
            this.shift = shift;
            this.lnShift = lnShift;
        }

        public Sin() {
            coef = lnCoef = 1;
            shift = lnShift = 0;
        }

        @Override
        public double calc(double x) {
            return coef * Math.sin(lnCoef * x + lnShift) + shift;
        }

        @Override
        public DoubleFunction prod(double lambda) {
            return new Sin(coef * lambda, lnCoef, shift * lambda, lnShift);
        }

        @Override
        public DoubleFunction iprod(double lambda) {
            coef *= lambda;
            shift *= lambda;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(MathMachine.numberFormat.format(coef))
                    .append("\\sin\\left(").append(MathMachine.numberFormat.format(lnCoef))
                    .append("*x");
            appendNumber(lnShift, builder);
            builder.append("\\right)");
            appendNumber(shift, builder);
            return builder.toString();
        }

        @Override
        public DoubleFunction deepcopy() {
            return new Sin(coef, lnCoef, shift, lnShift);
        }
    }
    //endregion
}
