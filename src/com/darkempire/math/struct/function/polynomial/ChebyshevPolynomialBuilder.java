package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 14.10.14.
 *
 * @author siredvin
 */
public abstract class ChebyshevPolynomialBuilder implements IInteratedPolynomialBuilder<DoublePolynomial> {
    protected List<DoublePolynomial> polynomials;

    public ChebyshevPolynomialBuilder() {
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
     * Обчислює наступний поліном Чебишева, через два попередніх обчислених за рекурентною формулою
     * використовуючи абстрактний метод impl_next();
     *
     * @return наступний поліном
     * @see com.darkempire.math.struct.function.polynomial.ChebyshevPolynomialBuilder#impl_next
     */
    @Override
    public DoublePolynomial calcNext() {
        int k = polynomials.size() - 1;
        DoublePolynomial lk_1 = polynomials.get(k - 1), lk = polynomials.get(k);
        DoublePolynomial lkP1 = impl_next(lk_1, lk);
        polynomials.add(lkP1);
        return lkP1;
    }

    /**
     * Обчислює поліном за індексом і , або повертає такий, якщо він вже був обчислений
     *
     * @param index індекс і
     * @return L_{i}(x)
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
     * Абстрактний метод, який допомагає розрізняти многочлени Чебишева першого та другого роду та знаходити
     * наступний з двох попередніх
     *
     * @param lk_1 Поліном з індексом k-1
     * @param lk   Поліном з індексом k
     * @return Поліном з індексом k +1
     */
    protected abstract DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk);

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
    private static class FirstKind extends ChebyshevPolynomialBuilder {
        public FirstKind() {
            super();
            polynomials.add(new DoublePolynomial(1));
            polynomials.add(new DoublePolynomial(0, 1));
        }

        @Override
        protected DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk) {
            return new DoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1);
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
    private static class SecondKind extends ChebyshevPolynomialBuilder {
        public SecondKind() {
            super();
            polynomials.add(new DoublePolynomial(1));
            polynomials.add(new DoublePolynomial(0, 2));
        }

        @Override
        protected DoublePolynomial impl_next(DoublePolynomial lk_1, DoublePolynomial lk) {
            return new DoublePolynomial(0, 2).imultiply(lk).isubtract(lk_1);
        }
    }

    public static ChebyshevPolynomialBuilder createFirstKindBuilder() {
        return new FirstKind();
    }

    public static ChebyshevPolynomialBuilder createSecondKingBuilder() {
        return new SecondKind();
    }
}