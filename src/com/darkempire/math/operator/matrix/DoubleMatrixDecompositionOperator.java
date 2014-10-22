package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.exception.MatrixTypeException;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrixIndexHolder;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.ArrayList;
import java.util.List;

import static com.darkempire.math.struct.matrix.DoubleMatrixIndexHolder.indexWrap;

/**
 * Created by siredvin on 17.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleMatrixDecompositionOperator {
    private DoubleMatrixDecompositionOperator() {
    }

    //region Декомпозиція додатньовизначенних та невироджених матриць (Холецького)

    /**
     * Реалізує розклад Холецького.
     * Він знаходиться за формулою A = L.L^T
     * Де, L - нижньотрикутна матриця, а A - додатньовизначена та невироджена
     * Точне значення знаходиться за формулами:
     * <p>
     * l_{j,j} = \sqrt{a_{j,j} - \sum\limits_{k=1}^{j-1} l_{j,k}^2}
     * l_{i,j} = \cfrac1{l_{j,j}} \left( a_{i,j} - \sum\limits_{k=1}^{j-1} l_{i,k} l_{j,k} \right),~i>j
     *
     * @param a матрица A
     * @return матрицю L
     */
    public static DoubleMatrix choleskyDecomposition(DoubleMatrix a) {
        int size = a.getColumnCount();
        if (size != a.getRowCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix result = DoubleFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= rowIndex; columnIndex++) {
                double value;
                double temp = a.get(rowIndex, columnIndex);
                if (rowIndex == columnIndex) {
                    double temp1 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        //l_{j,k}
                        double temp2 = result.get(columnIndex, k);
                        temp1 += temp2 * temp2;
                    }
                    temp1 = temp - temp1;
                    if (temp1 < 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_POSITIVE);
                    }
                    value = Math.sqrt(temp1);
                } else {
                    //l_{j,j}
                    double temp1 = result.get(columnIndex, columnIndex);
                    if (temp1 == 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_DEFINITE);
                    }
                    double temp2 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        temp2 += result.get(rowIndex, k) * result.get(columnIndex, k);
                    }
                    value = (temp - temp2) / temp1;
                }
                result.set(rowIndex, columnIndex, value);
            }
        }
        return result;
    }
    //endregion

    //region Декомпозиція невироджених матриць (LDL)

    /**
     * Реалізує розклад LDL.
     * Він знаходиться за формулою A = L.D.L^T
     * Де, L - нижньотрикутна матриця на головній діагоналі якої одинички,
     * D - діагональна матриця, а A - невироджена
     * Точне значення знаходиться за формулами:
     * <p>
     * D_j = a_{j,j} - \sum\limits_{k=1}^{j-1} l_{j,k}^2 D_k
     * l_{i,j} = \cfrac1{D_j} \left( a_{i,j} - \sum\limits_{k=1}^{j-1} l_{i,k} l_{j,k} D_k \right),~i>j
     *
     * @param a матрица A
     * @return матрицю L
     */
    public static LDLDecompositionResult LDLDecomposition(DoubleMatrix a) {
        int size = a.getColumnCount();
        if (size != a.getRowCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix d = DoubleFixedMatrix.createInstance(size, size);
        DoubleMatrix l = DoubleFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            l.set(rowIndex, rowIndex, 1);
            for (int columnIndex = 0; columnIndex <= rowIndex; columnIndex++) {
                double temp = a.get(rowIndex, columnIndex);
                if (rowIndex == columnIndex) {
                    double temp1 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        //l_{j,k}
                        double temp2 = l.get(columnIndex, k);
                        temp1 += temp2 * temp2 * d.get(k, k);
                    }
                    temp1 = temp - temp1;
                    d.set(rowIndex, columnIndex, temp1);
                } else {
                    //l_{j,j}
                    double temp1 = d.get(columnIndex, columnIndex);
                    if (temp1 == 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_DEFINITE);
                    }
                    double temp2 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        temp2 += l.get(rowIndex, k) * l.get(columnIndex, k) * d.get(k, k);
                    }
                    l.set(rowIndex, columnIndex, (temp - temp2) / temp1);
                }
            }
        }
        return new LDLDecompositionResult(l, d);
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
     * @param a матрица A
     * @return матриці B та С
     */
    public static SkeletonDecompositionResult skeletonDecomposition(DoubleMatrix a) {
        return skeletonDecomposition(a, false);
    }
    //endregion

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
     * @param a                  матрица A
     * @param withCPseudoInverse Чи повертати у результаті псевдообернену матрицю C
     * @return матриці B та С
     */
    public static SkeletonDecompositionResult skeletonDecomposition(DoubleMatrix a, boolean withCPseudoInverse) {
        DoubleMatrixIndexHolder aIndexHolder = indexWrap(a.clone());
        DoubleMatrixTransformOperator.makeUpperTrapeze(aIndexHolder, true);
        int rank = DoubleMatrixMathOperator.calcUnZeroDiagonalElementCount(aIndexHolder);
        int rowCount = a.getRowCount();
        int columnCount = a.getColumnCount();
        int size = Math.min(rowCount, columnCount);
        //Якщо ранг матриці дорівнює її розмірності, то розклад стає елеменатарним.
        //Одна з матриць - це сама матриця A, а інша - діагональна
        if (rank == size) {
            if (size == rowCount) {//Якщо кількість рядків (тобто n) менше за кількість стовчиків, то одиничною буде матриця B
                return new SkeletonDecompositionResult(DoubleMatrixGenerateOperator.generateFixedDiagonalMatrix(size), a.clone(), null);
            } else {//Інакше матриця C
                return new SkeletonDecompositionResult(a.clone(), DoubleMatrixGenerateOperator.generateFixedDiagonalMatrix(size), null);
            }
        }
        //Далі продовжуватися буде лише тоді, коли розмірності матриці A більше за її ранг
        //Обираємо усі лінійнонезалежні вектори
        //Їх індекси як раз будуть першими у трапецієвидній матриці, що утворена з A
        List<IDoubleVectorProvider> vectorList = new ArrayList<>();
        for (int i = 0; i < rank; i++) {
            vectorList.add(a.row(aIndexHolder.getRowIndex(i)));
        }
        DoubleMatrix c = DoubleMatrixGenerateOperator.fixedFromRows(vectorList);
        vectorList.clear();
        //Далі за формулами рахуємо B
        DoubleMatrix c_tr = c.transpose();
        DoubleMatrix c_p = c_tr.multy(DoubleMatrixInverseOperator.inverse(c.multy(c_tr)));
        DoubleMatrix b = a.multy(c_p);
        return new SkeletonDecompositionResult(b, c, withCPseudoInverse ? c_p : null);
    }

    public static class LDLDecompositionResult {
        public DoubleMatrix L;
        public DoubleMatrix D;

        protected LDLDecompositionResult(DoubleMatrix l, DoubleMatrix d) {
            L = l;
            D = d;
        }
    }

    public static class SkeletonDecompositionResult {
        public DoubleMatrix B;
        public DoubleMatrix C;
        public DoubleMatrix CPseudoInverse;

        protected SkeletonDecompositionResult(DoubleMatrix b, DoubleMatrix c, DoubleMatrix cPseudoInverse) {
            B = b;
            C = c;
            CPseudoInverse = cPseudoInverse;
        }
    }

    //endregion
}
