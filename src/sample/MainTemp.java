package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.FuzzyRelationMatrix;
import com.darkempire.math.struct.set.FuzzyNumberSet;
import com.darkempire.math.utils.SetUtils;

/**
 * Create in 14:50
 * Created by siredvin on 21.05.14.
 */
@AnjiUtil
public final class MainTemp {
    private MainTemp() {
    }

    public static void main(String[] args) {
        FuzzyRelationMatrix r1 = FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                1, 1, 0, 1,
                1, 1, 0, 1,
                1, 1, 1, 1,
                0, 0, 0, 1
        }), r2 = FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                1, 0, 1, 1,
                1, 1, 1, 1,
                0, 0, 1, 0,
                0, 0, 1, 1
        }), r3 = FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                1, 1, 1, 1,
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 1
        }), r4 = FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                1, 1, 1, 1,
                1, 1, 1, 1,
                0, 0, 1, 1,
                0, 0, 1, 1
        });
        FuzzyRelationMatrix matrix = r1.iprod(0.4).iadd(r2.iprod(0.3)).iadd(r3.iprod(0.2)).iadd(r4.iprod(0.1));
        Log.log(Log.debugIndex, matrix);
        FuzzyRelationMatrix s = SetUtils.buildStrong(matrix);
        Log.log(Log.debugIndex, s);
        FuzzyNumberSet nd = SetUtils.buildNotDominate(s);
        Log.log(Log.debugIndex, nd);
        FuzzyRelationMatrix matrix1 = FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                1, 0, 0, 1,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
        FuzzyRelationMatrix matrix2 = SetUtils.buildStrong(matrix1);
        Log.log(Log.debugIndex, matrix2);
        FuzzyNumberSet nd1 = SetUtils.buildNotDominate(matrix2);
        Log.log(Log.debugIndex, nd1);
    }
}
