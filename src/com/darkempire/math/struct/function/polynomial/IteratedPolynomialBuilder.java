package com.darkempire.math.struct.function.polynomial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 16.10.14.
 *
 * @author siredvin
 */
public abstract class IteratedPolynomialBuilder {
    public static enum PolynomialType {
        /**
         * Многочлен Чебишева першого роду
         */
        CHEBYSHEV_FIRST_KIND,
        /**
         * Многочлен Чебишева другого роду
         */
        CHEBYSHEV_SECOND_KIND,
        /**
         * Многочлен Ерміта у ймовірнісному означенні
         */
        HERMITE_PROBABILISTS,
        /**
         * Многочлен Ерміта у фізичному означенні
         */
        HERMITE_PHYSICISTS,
        /**
         * Многочлен Лаггера
         */
        LAGUERRE,
        /**
         * Многочлен Лежандра
         */
        LEGENDRE
    }

    //region Загальна імплементація
    protected List<ArrayDoublePolynomial> polynomials;

    protected IteratedPolynomialBuilder() {
        polynomials = new ArrayList<>();
    }

    /**
     * @return Індекс останнього поліному, який був обчислений
     */
    public int getMaxIndex() {
        return polynomials.size() - 1;
    }

    /**
     * Обчислює наступний поліном, через два попередніх обчислених за рекурентною формулою
     * використовуючи абстрактний метод impl_next();
     *
     * @return наступний поліном
     * @see com.darkempire.math.struct.function.polynomial.IteratedPolynomialBuilder#impl_next
     */
    public ArrayDoublePolynomial calcNext() {
        int k = polynomials.size() - 1;
        ArrayDoublePolynomial lk_1 = polynomials.get(k - 1), lk = polynomials.get(k);
        ArrayDoublePolynomial lkP1 = impl_next(lk_1, lk, k);
        polynomials.add(lkP1);
        return lkP1;
    }

    /**
     * Обчислює поліном за індексом і , або повертає такий, якщо він вже був обчислений
     *
     * @param index індекс і
     * @return H_{i}(x)
     */
    public ArrayDoublePolynomial calcIndexed(int index) {
        int size = polynomials.size();
        if (index < size) {
            return polynomials.get(index);
        }
        int calc_count = index - size + 1;
        ArrayDoublePolynomial result = null;
        for (int i = 0; i < calc_count; i++) {
            result = calcNext();
        }
        return result;
    }

    public void clearCache() {
        polynomials.clear();
    }

    /**
     * Абстрактний метод, який допомагає розрізняти многочлени різних типів та знаходити
     * наступний з двох попередніх
     *
     * @param lk_1 Поліном з індексом k-1
     * @param lk   Поліном з індексом k
     * @param k    Власне, індекс
     * @return Поліном з індексом k +1
     */
    protected abstract ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k);
    //endregion

    //region Поліноми Ерміта

    /**
     * Клас, який відповідає поліномам Ерміта у їх ймовірнісній формі. Послідовність така:
     * H_0(x) = 1
     * H_1(x) = x
     * H_{n+1}(x) = x H_n(x) + n H_{n-1}(x)
     * <p>
     * Самі ці поліноми визначаються як:
     * H_n(x) = (-1)^n e^{\frac{x^2}{2}} \cfrac{d^n}{d x^n} e^{-\frac{x^2}{2}}
     */
    private static class HermiteProbabilistsPolynomialBuilder extends IteratedPolynomialBuilder {
        public HermiteProbabilistsPolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(0, 1));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return new ArrayDoublePolynomial(0, 1).imultiply(lk).isubtract(lk_1.prop(k));
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
    private static class HermitePhysicistsPolynomialBuilder extends IteratedPolynomialBuilder {
        public HermitePhysicistsPolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(0, 2));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return new ArrayDoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1.prop(k * 2));
        }
    }
    //endregion

    //region Поліноми Чебишева

    /**
     * Клас, який відповідає многочленам Чебишева першого роду.
     * Послідовність складається так:
     * T_0(x) = 1
     * T_1(x) = x
     * T_{k+1}(x) = 2x T_k(x) - T_{k-1}(x)
     * <p>
     * Самі ці поліноми знаходяться за точною формулою:
     * T_k(x) = \sum\limits_{n=0}^{[n\2]} C_{2n}^k (x^2-1)^n x^{k-2n}
     */
    private static class ChebyshevFirstKindPolynomialBuilder extends IteratedPolynomialBuilder {
        public ChebyshevFirstKindPolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(0, 1));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return new ArrayDoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1);
        }
    }

    /**
     * Клас, який відповідає многочленам Чебишева першого роду.
     * Послідовність складається так:
     * U_0(x) = 1
     * U_1(x) = 2x
     * U_{k+1}(x) = 2x U_k(x) - U_{k-1}(x)
     * <p>
     * Самі ці поліноми знаходяться за точною формулою:
     * U_k(x) = \sum\limits_{n=0}^{[n\2]} C_{2n+1}^{k+1} (x^2-1)^n x^{k-2n}
     */
    private static class ChebyshevSecondKindPolynomialBuilder extends IteratedPolynomialBuilder {
        public ChebyshevSecondKindPolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(0, 2));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return new ArrayDoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1);
        }
    }
    //endregion

    //region Поліноми Лаггера

    /**
     * Клас, який відповідає многочленам Лаггера.
     * Послідовність складається так:
     * L_0(x) = 1
     * L_1(x) = 1 - x
     * L_{k+1}(x) = (1/(k+1)) * ((2k+1-x)*L_k(x) - k*L_{k-1}(x))
     * <p>
     * Самі ці поліноми визначаються як:
     * L_n(x) = \cfrac{e^x}{n!} \cfrac{d^n}{dx^n} \left( e^{-x} x^n \right)
     */
    private static class LaguerrePolynomialBuilder extends IteratedPolynomialBuilder {
        public LaguerrePolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(1, -1));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return lk.multiply(new ArrayDoublePolynomial(2 * k + 1, -1)).isubtract(lk_1.prop(k)).iprop(1.0 / (k + 1));
        }
    }
    //endregion

    //region Поліноми Лежандра

    /**
     * Клас, який відповідає многочленам Лежандра.
     * Послідовність складається так:
     * P_0(x) = 1
     * P_1(x) = x
     * P_{k+1}(x) = \cfrac{2k+1}{k+1}xP_k(x) - \cfrac{k}{k+1} P_{k-1}(x)
     * <p>
     * Самі ці поліноми визначаються як:
     * P_n(x) = \cfrac{1}{2^n n!} \cfrac{d^n}{dz^n} \left( z^2-1 \right)^n
     */
    private static class LegendrePolynomialBuilder extends IteratedPolynomialBuilder {
        private LegendrePolynomialBuilder() {
            super();
            polynomials.add(new ArrayDoublePolynomial(1));
            polynomials.add(new ArrayDoublePolynomial(0, 1));
        }

        @Override
        protected ArrayDoublePolynomial impl_next(ArrayDoublePolynomial lk_1, ArrayDoublePolynomial lk, int k) {
            return new ArrayDoublePolynomial(0, (2.0 * k + 1) / (k + 1)).imultiply(lk).isubtract(lk_1.prop(k / (k + 1.0)));
        }
    }
    //endregion


    public static IteratedPolynomialBuilder createBuilder(PolynomialType type) {
        switch (type) {
            case CHEBYSHEV_FIRST_KIND:
                return new ChebyshevFirstKindPolynomialBuilder();
            case CHEBYSHEV_SECOND_KIND:
                return new ChebyshevSecondKindPolynomialBuilder();
            case HERMITE_PROBABILISTS:
                return new HermiteProbabilistsPolynomialBuilder();
            case HERMITE_PHYSICISTS:
                return new HermitePhysicistsPolynomialBuilder();
            case LAGUERRE:
                return new LaguerrePolynomialBuilder();
            case LEGENDRE:
                return new LegendrePolynomialBuilder();
        }
        return null;
    }
}
