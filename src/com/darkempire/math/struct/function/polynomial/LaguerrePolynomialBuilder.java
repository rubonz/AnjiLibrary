package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.log.Log;
import com.darkempire.math.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
public class LaguerrePolynomialBuilder implements IInteratedPolynomialBuilder<DoublePolynomial> {
    private static final DoublePolynomial L_0 = new DoublePolynomial(1);
    private static final DoublePolynomial L_1 = new DoublePolynomial(1, -1);

    private List<DoublePolynomial> polynomials;

    public LaguerrePolynomialBuilder() {
        polynomials = new ArrayList<>();
        polynomials.add(L_0.deepcopy());
        polynomials.add(L_1.deepcopy());
    }

    /**
     * @return Індекс останнього поліному, який був обчислений
     */
    @Override
    public int getMaxIndex() {
        return polynomials.size() - 1;
    }

    /**
     * Обчислює наступний поліном Лаггера, через два попередніх обчислених за рекурентною формулою:
     * L_{k+1}(x) = (1/(k+1)) * ((2k+1-x)*L_k(x) - k*L_{k-1}(x))
     *
     * @return L_{k+1}(x)
     */
    @Override
    public DoublePolynomial calcNext() {
        int k = polynomials.size() - 1;
        DoublePolynomial lk_1 = polynomials.get(k - 1), lk = polynomials.get(k);
        DoublePolynomial lkP1 = lk.multiply(new DoublePolynomial(2 * k + 1, -1)).isubtract(lk_1.prop(k)).iprop(1.0 / (k + 1));
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
     * Обчислює поліном Лаггера за індексом, не використовуючи ітеративну формулу, а за прямою.
     * L_n(x) = \sum\limits_{k=0}^n C_k^n \cfrac{(-1)^k}{k!} x^k
     * @param index індекс поліному (n)
     * @return L_n(x)
     */
    public static DoublePolynomial calcStandalone(int index) {
        DoublePolynomial result = new DoublePolynomial(new double[index + 1]);
        int[] factArr = MathUtils.factArr(index);
        int one = 1;
        int n = index;
        index++;
        double factN = factArr[n];
        for (int k = 0; k < index; k++) {
            result.set(k, one * factN / (factArr[k] * factArr[k] * factArr[n - k]));
            one *= -1;
        }
        return result;
    }

}
