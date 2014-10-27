package sample.generators;

import com.darkempire.anji.document.wolfram.util.WolframConvertUtils;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkFactory;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
public class TestMathemitaGenerator {
    private static int log = Log.addString("Result Log");

    public static void main(String[] args) throws Exception {
        Log.setHidePrefix(log, true);
        KernelLink ml = MathLinkFactory.createKernelLink("-linkmode launch -linkname 'math -mathlink'");
        ml.discardAnswer();
        //generateDoubleMatrixPseudoInverseGrevilleMethodTest(ml, 10, 1E-8, 3, 15, 3, 15);
        generateDoubleMatrixInverseGaussianMethodTest(ml, 10, 1e-8, 3, 15);
    }

    public static void generateDoubleMatrixPseudoInverseGrevilleMethodTest(KernelLink ml, int count, double eps, int minRowCount, int maxRowCount, int minColumnCount, int maxColumnCount) {
        Random random = new Random();
        final int rowCountDelta = maxRowCount - minRowCount;
        final int columnCountDelta = maxColumnCount - minColumnCount;
        final double maxValue = 1;
        Log.log(log, "DoubleMatrix a,atest;");
        for (int i = 0; i < count; i++) {
            int rowCount = random.nextInt(rowCountDelta) + minRowCount;
            int columnCount = random.nextInt(columnCountDelta) + minColumnCount;
            double[] temp = new double[rowCount * columnCount];
            DoubleMatrix matrix = DoubleFixedMatrix.createInstance(rowCount, columnCount, temp);
            matrix.fill((rowIndex, columnIndex) -> random.nextDouble() * maxValue);
            ml.evaluateToInputForm("Clear[A]", 0);
            ml.evaluateToInputForm("A = " + WolframConvertUtils.convertDoubleMatrix(matrix), 0);
            String result = ml.evaluateToInputForm("PseudoInverse[A]", 0).replaceAll("\\{|\\}", "").replace("*^", "E");
            Log.log(log, "a = DoubleFixedMatrix.createInstance(", rowCount, ',', columnCount, ",new double[]{", Arrays.toString(temp).replaceAll("\\[|\\]", ""), "});");
            Log.log(log, "atest = DoubleFixedMatrix.createInstance(", columnCount, ',', rowCount, ",new double[]{", result, "});");
            Log.log(log, "assertThat(pseudoInverse(a,PseudoInverseMethodType.GREVILLE_METHOD),equalsEps(atest,", eps, "));");
        }
    }

    public static void generateDoubleMatrixInverseGaussianMethodTest(KernelLink ml, int count, double eps, int minSize, int maxSize) {
        Random random = new Random();
        final int sizeDelta = maxSize - minSize;
        final double maxValue = 1;
        Log.log(log, "DoubleMatrix a,atest;");
        for (int i = 0; i < count; i++) {
            int size = random.nextInt(sizeDelta) + minSize;
            double[] temp = new double[size * size];
            DoubleMatrix matrix = DoubleFixedMatrix.createInstance(size, size, temp);
            matrix.fill((rowIndex, columnIndex) -> random.nextDouble() * maxValue);
            ml.evaluateToInputForm("Clear[A]", 0);
            ml.evaluateToInputForm("A = " + WolframConvertUtils.convertDoubleMatrix(matrix), 0);
            String result = ml.evaluateToInputForm("PseudoInverse[A]", 0).replaceAll("\\{|\\}", "").replace("*^", "E");
            Log.log(log, "a = DoubleFixedMatrix.createInstance(", size, ',', size, ",new double[]{", Arrays.toString(temp).replaceAll("\\[|\\]", ""), "});");
            Log.log(log, "atest = DoubleFixedMatrix.createInstance(", size, ',', size, ",new double[]{", result, "});");
            Log.log(log, "assertThat(inverse(a,InverseMethodType.GAUSSIAN_ELIMINATION),equalsEps(atest,", eps, "));");
        }
    }
}
