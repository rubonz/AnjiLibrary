package com.darkempire.math.struct.function.polynomial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 15.10.14.
 *
 * @author siredvin
 */
public abstract class HermitePolynomialBuilder implements IInteratedPolynomialBuilder<DoublePolynomial> {
    protected List<DoublePolynomial> polynomials;

    protected HermitePolynomialBuilder() {
        polynomials = new ArrayList<>();
    }

    /**
     * @return Індекс останнього поліному, який був обчислений
     */
    @Override
    public int getMaxIndex() {
        return polynomials.size() - 1;
    }

    /**
     * Обчислює наступний поліном Ерміта, через два попередніх обчислених за рекурентною формулою
     * використовуючи абстрактний метод impl_next();
     *
     * @return наступний поліном
     * @see com.darkempire.math.struct.function.polynomial.ChebyshevPolynomialBuilder#impl_next
     */
    @Override
    public DoublePolynomial calcNext() {
        int k = polynomials.size() - 1;
        DoublePolynomial lk_1 = polynomials.get(k - 1), lk = polynomials.get(k);
        DoublePolynomial lkP1 = impl_next(lk_1, lk, k);
        polynomials.add(lkP1);
        return lkP1;
    }

    /**
     * Обчислює поліном за індексом і , або повертає такий, якщо він вже був обчислений
     *
     * @param index індекс і
     * @return H_{i}(x)
     */
    @Override
    public DoublePolynomial calcIndexed(int index) {
        int size = polynomials.size();
        if (index < size) {
            return polynomials.get(index);
        }
        int calc_count = index - size + 1;
        DoublePolynomial result = null;
        for (int i = 0; i < calc_count; i++) {
            result = calcNext();
        }
        return result;
    }

    /**
     * Абстрактний метод, який допомагає розрізняти многочлени Ерміта першого та другого роду та знаходити
     * наступний з двох попередніх
     *
     * @param lk_1 Поліном з індексом k-1
     * @param lk   Поліном з індексом k
     * @param k    Власне, індекс
     * @return Поліном з індексом k +1
     */
    protected abstract DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk, int k);

    /**
     * Клас, який відповідає поліномам Ерміта у їх ймовірнісній формі. Послідовність така:
     * H_0(x) = 1
     * H_1(x) = x
     * H_{n+1}(x) = x H_n(x) + n H_{n-1}(x)
     * <p>
     * Самі ці поліноми визначаються як:
     * H_n(x) = (-1)^n e^{\frac{x^2}{2}} \cfrac{d^n}{d x^n} e^{-\frac{x^2}{2}}
     */
    private static class ProbabilistsPolynomial extends HermitePolynomialBuilder {
        public ProbabilistsPolynomial() {
            super();
            polynomials.add(new DoublePolynomial(1));
            polynomials.add(new DoublePolynomial(0, 1));
        }

        @Override
        protected DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk, int k) {
            return new DoublePolynomial(0, 1).imultiply(lk).isubtract(lk_1.prop(k));
        }
    }

    /**
     * Клас, який відповідає поліномам Ерміта у їх фізичній формі. Послідовність така:
     * H_0(x) = 1
     * H_1(x) = 2x
     * H_{n+1}(x) = 2x H_n(x) + 2n H_{n-1}(x)
     * <p>
     * Самі ці поліноми визначаються як:
     * H_n(x) = (-1)^n e^{x^2} \cfrac{d^n}{d x^n} e^{-x^2}
     */
    private static class PhysicistsPolynomial extends HermitePolynomialBuilder {
        public PhysicistsPolynomial() {
            super();
            polynomials.add(new DoublePolynomial(1));
            polynomials.add(new DoublePolynomial(0, 2));
        }

        @Override
        protected DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk, int k) {
            return new DoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1.prop(k * 2));
        }
    }

    public static HermitePolynomialBuilder createPropabilistsBuilder() {
        return new ProbabilistsPolynomial();
    }

    public static HermitePolynomialBuilder createPhysicistsBuilder() {
        return new PhysicistsPolynomial();
    }
}
