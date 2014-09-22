package sample;

import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.utils.WolframConvertUtils;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

import static java.lang.System.out;

import java.util.Random;

/**
 * Create in 0:33
 * Created by siredvin on 31.05.14.
 */
public class MathMain {
    private int result = 3;

    public static void main(String[] args) {
        KernelLink ml = null;
        try {
            ml = MathLinkFactory.createKernelLink(args);
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            return;
        }

        try {
            // Get rid of the initial InputNamePacket the kernel will send
            // when it is launched.
            ml.discardAnswer();
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                DoubleFixedMatrix matrix;
                int size = /*rand.nextInt(9)+1*/3;
                double[] array = new double[size * size];
                for (int j = 0; j < array.length; j++) {
                    array[j] = rand.nextDouble() * 100;
                }
                matrix = DoubleFixedMatrix.createInstance(size, size, array);
                //DoubleMatrixOperator.makeLowerTriangle(matrix);
                String temp = ml.evaluateToInputForm("RowReduce[" + WolframConvertUtils.convertDoubleMatrix(matrix) + "]", 0);
                //System.out.println(WolframConvertUtils.convertDoubleMatrix(matrix));
                temp = temp.replaceAll("\\{|\\}", "");
                out.print("assertEquals(DoubleMatrixOperator.makeUpperTriangle(FixedDoubleMatrix.createInstance(");
                out.print(size);
                out.print(',');
                out.print(size);
                out.print(",new double[]{");
                for (int j = 0; j < array.length; j++) {
                    System.out.print(array[j]);
                    if (j != array.length - 1) {
                        System.out.print(',');
                    }
                }
                out.print("})),FixedDoubleMatrix.createInstance(");
                out.print(size);
                out.print(',');
                out.print(size);
                out.print(",new double[]{");
                out.print(temp);
                out.println("}));");
            }
        } catch (MathLinkException e) {
            System.out.println("MathLinkException occurred: " + e.getMessage());
        } finally {
            ml.close();
        }
    }
}
