package sample;

import com.darkempire.anji.document.tex.TeXDocumentManager;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.document.tex.TeXTableObject;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.matrix.BooleanFixedMatrix;
import com.darkempire.math.struct.matrix.BooleanMatrix;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.set.CombinateDoubleInterval;
import com.darkempire.math.struct.set.DoubleInterval;
import com.darkempire.math.struct.set.SimpleDoubleInterval;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
// Code below doesn't work for 0 and NaN - just check before


// Here you have to simplify the fraction
        DoubleInterval i = new CombinateDoubleInterval(new SimpleDoubleInterval(5, 6), new SimpleDoubleInterval(5.5, 6.5), new SimpleDoubleInterval(6.5, 7));
        Log.log(Log.debugIndex, i);
    }

}
