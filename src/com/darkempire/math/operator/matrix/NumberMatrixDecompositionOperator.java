package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.matrix.NumberMatrixIndexHolder;
import com.darkempire.math.struct.vector.IVectorProvider;

import java.util.ArrayList;
import java.util.List;

import static com.darkempire.math.struct.matrix.NumberMatrixIndexHolder.indexWrap;

/**
 * Created by siredvin on 17.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class NumberMatrixDecompositionOperator {
    private NumberMatrixDecompositionOperator() {
    }

    //region Декомпозиція прямокутних матриць (скелетна декомпозиція)

    /**
     * Виконується скелетний розклад матриці А.
     * Нехай ранг матриці А rank(A) = r;
     * Сама матриця A розмірності n x m
     * Тоді, ми розкладаємо матрицю A на дві матриці B та C так, щоб
     * A = B * C при чому rank(B) = rank(C) = rank(A) = r
     * та C має розмірність r x m, а розмірність B - n x r
     * <p>
     * Матриці B та C обчислюються за таким алгоритмом:
     * 1. Знаходяться r лінійнонезалежних рядків матриці A і з них складаємо матрицю C
     * 2. Після чого знаходимо матрицю B за формулою : B = A.C^+ де C^+ - псевдообернена матриця для C
     * Обчислюється за формулою: С^t * (C*C^t)^{-1}
     *
     * @param a матрица A
     * @return матриці B та С
     */
    public static <T extends com.darkempire.math.struct.Number<T>> SkeletonDecompositionResult skeletonDecomposition(NumberMatrix<T> a) {
        return skeletonDecomposition(a, false);
    }

    /**
     * Виконується скелетний розклад матриці А.
     * Нехай ранг матриці А rank(A) = r;
     * Сама матриця A розмірності n x m
     * Тоді, ми розкладаємо матрицю A на дві матриці B та C так, щоб
     * A = B * C при чому rank(B) = rank(C) = rank(A) = r
     * та C має розмірність r x m, а розмірність B - n x r
     * <p>
     * Матриці B та C обчислюються за таким алгоритмом:
     * 1. Знаходяться r лінійнонезалежних рядків матриці A і з них складаємо матрицю C
     * 2. Після чого знаходимо матрицю B за формулою : B = A.C^+ де C^+ - псевдообернена матриця для C
     * Обчислюється за формулою: С^t * (C*C^t)^{-1}
     *
     * @param a                  матрица A
     * @param withCPseudoInverse Чи повертати у результаті псевдообернену матрицю C
     * @return матриці B та С
     */
    public static <T extends Number<T>> SkeletonDecompositionResult<T> skeletonDecomposition(NumberMatrix<T> a, boolean withCPseudoInverse) {
        NumberMatrixIndexHolder<T> aIndexHolder = indexWrap(a.clone());
        NumberMatrixTransformOperator.makeUpperTrapeze(aIndexHolder, true);
        int rank = NumberMatrixMathOperator.calcUnZeroDiagonalElementCount(aIndexHolder);
        int rowCount = a.getRowCount();
        int columnCount = a.getColumnCount();
        int size = Math.min(rowCount, columnCount);
        //Якщо ранг матриці дорівнює її розмірності, то розклад стає елеменатарним.
        //Одна з матриць - це сама матриця A, а інша - діагональна
        if (rank == size) {
            if (size == rowCount) {//Якщо кількість рядків (тобто n) менше за кількість стовчиків, то одиничною буде матриця B
                return new SkeletonDecompositionResult<T>(NumberMatrixGenerateOperator.generateFixedDiagonalMatrix(size, a.get(0, 0)), a.clone(), null);
            } else {//Інакше матриця C
                return new SkeletonDecompositionResult<T>(a.clone(), NumberMatrixGenerateOperator.generateFixedDiagonalMatrix(size, a.get(0, 0)), null);
            }
        }
        //Далі продовжуватися буде лише тоді, коли розмірності матриці A більше за її ранг
        //Обираємо усі лінійнонезалежні вектори
        //Їх індекси як раз будуть першими у трапецієвидній матриці, що утворена з A
        List<IVectorProvider<T>> vectorList = new ArrayList<>();
        for (int i = 0; i < rank; i++) {
            vectorList.add(a.row(aIndexHolder.getRowIndex(i)));
        }
        NumberMatrix<T> c = NumberMatrixGenerateOperator.fixedFromRows(vectorList);
        vectorList.clear();
        //Далі за формулами рахуємо B
        NumberMatrix<T> c_tr = c.transpose();
        NumberMatrix<T> c_p = c_tr.multy(NumberMatrixInverseOperator.inverse(c.multy(c_tr)));
        NumberMatrix<T> b = a.multy(c_p);
        return new SkeletonDecompositionResult<>(b, c, withCPseudoInverse ? c_p : null);
    }

    public static class SkeletonDecompositionResult<T extends Number<T>> {
        public NumberMatrix<T> B;
        public NumberMatrix<T> C;
        public NumberMatrix<T> CPseudoInverse;

        protected SkeletonDecompositionResult(NumberMatrix<T> b, NumberMatrix<T> c, NumberMatrix<T> CPseudoInverse) {
            B = b;
            C = c;
            this.CPseudoInverse = CPseudoInverse;
        }
    }

    //endregion
}
