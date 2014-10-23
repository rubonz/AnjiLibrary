package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.MathMachine;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleVector;

import static com.darkempire.math.operator.matrix.DoubleMatrixMathOperator.addRow;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleMatrixInverseOperator {
    private DoubleMatrixInverseOperator() {
    }

    //region Обчислення обернених матриць
    public static DoubleMatrix inverseWithGauss(DoubleMatrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix temp = matrix.clone();
        DoubleMatrix result = DoubleMatrixGenerateOperator.generateDiagonalMatrix(rowCount, temp);
        //Починаємо цикл
        for (int index = 0; index < columnCount; index++) {
            double baseElement = temp.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    double nextElement = -temp.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(temp, rowIndex, index, nextElement);
                        addRow(result, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий, то матриця вироджена
                return null;
            }
        }
        //А тепер приводимо матрицю temp з діагонального до одиничного виду
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            double baseElement = temp.get(rowIndex, rowIndex);
            temp.operateRow(rowIndex, d -> d / baseElement);
            result.operateRow(rowIndex, d -> d / baseElement);
        }
        return result;
    }
    //endregion

    //region Обчислення псевдообернених матриць

    /**
     * Обчислення псевдооберненої матриці за допомогою скелетного розкладу матриці A
     * на матриці B та С і використання формули:
     * A^+ = C^+ * B^+ , причому A = BC
     *
     * @param matrix матриця A
     * @return A^+
     */
    @AnjiRewrite(reason = "Взагалі неправильно працює")
    public static DoubleMatrix pseudoInverseSkeleton(DoubleMatrix matrix) {
        DoubleMatrixDecompositionOperator.SkeletonDecompositionResult result = DoubleMatrixDecompositionOperator.skeletonDecomposition(matrix, true);
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (result.CPseudoInverse == null) {//Отже, ранг матриці A дорівнює одній з її розмірностей
            //Потрібно з’ясувати якій, адже для знаходження псевдооберненої матриці це має значення (праве або ліве множення)
            //Очевидно, що це буде мінімальний, тому просто знайдемо мінімальний серед двох
            if (rowCount < columnCount) {//Отже, матриця A прихована у матриці B.
                DoubleMatrix b = result.B;
                DoubleMatrix b_t = b.transpose();
                return inverse(b_t.multy(b)).multy(b_t);
            } else {//Отже, матриця A прихована у матриці С
                DoubleMatrix c = result.C;
                DoubleMatrix c_t = c.transpose();
                return c_t.multy(inverse(c.multy(c_t)));
            }
        }
        //Якщо ми дійшли сюди, то ранг матриці A менший за її розміри
        //Будуємо псевдообернену для матриці B
        DoubleMatrix b = result.B;
        DoubleMatrix b_t = b.transpose();
        DoubleMatrix b_i = inverse(b_t.multy(b));
        if (b_i == null) {
           /* Log.log(Log.debugIndex,"B=", WolframConvertUtils.convertDoubleMatrix(b));
            Log.log(Log.debugIndex,"A=",WolframConvertUtils.convertDoubleMatrix(matrix));
            Log.log(Log.debugIndex,"C=",WolframConvertUtils.convertDoubleMatrix(result.C));
            Log.log(Log.debugIndex,"Ci=",WolframConvertUtils.convertDoubleMatrix(result.CPseudoInverse));*/
        }
        DoubleMatrix b_p = b_i.multy(b_t);
        return result.CPseudoInverse.multy(b_p);
    }

    /**
     * Обчислення псевдооберненої матриці за методом Гревілля.
     * Загалом, псевдообернена матриця обчислюється по стовпчикам
     * A_{k} - матриця, утворена першими k стовбчиками матриці А
     * a_k - k-тий стовпчик матриці
     * Підготовка: A^{-1}_{1} = a_{1}^{-1}, якщо a_{1}!=0 інакше 0
     * Алгоритм:
     * 1. k=k+1
     * 2. d_k = A^{-1}_{k-1}*a_k
     * 3. c_k = a_k - A_{k-1}d_k
     * 4. if c_k!=0 : b_k = c_k^{-1} = (c_k^T c_k)^{-1} c_k^T
     * else b_k = (1+d_k^T d_k)^{-1} d_k^T A_{k-1}^{-1}
     * 5. B_k = A_{k-1}^{-1} - d_k b_k
     * 6. A_k^{-1} = (B_k|b_k)^T - тобто це просто доставлення рядка b_k до матриці B_k знизу
     *
     * @param matrix матриця A
     * @return псевдообернена матриця А
     */
    public static DoubleMatrix pseudoInverseGrevilleMethod(DoubleMatrix matrix) {
        int columnCount = matrix.getColumnCount();
        DoubleVector a1 = matrix.column(0);
        double value = a1.scalar(a1);
        if (Double.compare(value, 0) != 0) {
            a1 = a1.prod(1 / value);
        }
        DoubleMatrix pseudoInverse = DoubleMatrixGenerateOperator.fixedFromRows(a1);
        //Поки все правильно
        for (int k = 1; k < columnCount; k++) {
            //Log.log(Log.debugIndex,pseudoInverse);
            //Log.log(Log.debugIndex,k);
            DoubleMatrix aks1 = matrix.sliceColumnsTo(k);
            //Log.log(Log.debugIndex,aks1);
            DoubleVector ak = matrix.column(k);
            //Log.log(Log.debugIndex,"ak=",ak);
            DoubleVector dk = pseudoInverse.multy(ak);
            //Log.log(Log.debugIndex,"dk=",dk);
            //Log.log(Log.debugIndex,ak.getClass());
            DoubleVector ck = ak.subtract(aks1.multy(dk));
            //Log.log(Log.debugIndex,"ck=",ck);
            double ckck = ck.scalar(ck);
            DoubleVector bk;
            if (Math.abs(ckck) > MathMachine.MACHINE_PRACTICAL_EPS) {
                bk = ck.prod(1 / ckck);
            } else {
                double dkdkp1 = dk.scalar(dk) + 1;
                //Log.log(Log.debugIndex,dkdkp1);
                //Log.log(Log.debugIndex,dk.mult(pseudoInverse));
                bk = dk.mult(pseudoInverse).iprod(1 / dkdkp1);
            }
            //Log.log(Log.debugIndex,"bk=",bk);
            //Log.log(Log.debugIndex,dk.mult(bk));
            DoubleMatrix Bk = pseudoInverse.subtract(dk.mult(bk));
            //Log.log(Log.debugIndex,Bk);
            pseudoInverse = DoubleMatrixTransformOperator.appendRowF(Bk, bk);
        }
        return pseudoInverse;
    }
    //endregion

    /**
     * Будує обернену матрицю за заданим методом
     *
     * @param matrix матриця
     * @param type   метод
     * @return обернена матриця
     */
    public static DoubleMatrix inverse(DoubleMatrix matrix, InverseMethodType type) {
        DoubleMatrix inverse = null;
        switch (type) {
            case GAUSSIAN_ELIMINATION:
                inverse = inverseWithGauss(matrix);
                break;
        }
        return inverse;
    }

    public static DoubleMatrix inverse(DoubleMatrix matrix) {
        return inverse(matrix, InverseMethodType.GAUSSIAN_ELIMINATION);
    }

    public static DoubleMatrix pseudoInverse(DoubleMatrix matrix, PseudoInverseMethodType type) {
        DoubleMatrix pseudoInverse = null;
        switch (type) {
            case SKELETON_DECOMPOSITION:
                pseudoInverse = pseudoInverseSkeleton(matrix);
                break;
            case GREVILLE_METHOD:
                pseudoInverse = pseudoInverseGrevilleMethod(matrix);
                break;
        }
        return pseudoInverse;
    }

    public static DoubleMatrix pseudoInverse(DoubleMatrix matrix) {
        return pseudoInverse(matrix, PseudoInverseMethodType.GREVILLE_METHOD);
    }

}
