package sample;

import com.darkempire.anji.document.tex.TeXDocumentManager;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.document.tex.TeXTableObject;
import com.darkempire.math.struct.number.Fraction;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
// Code below doesn't work for 0 and NaN - just check before


// Here you have to simplify the fraction
        Fraction temp = new Fraction(-0.155f);
        System.out.println(temp);
        System.out.println(temp.doubleValue());
        TeXDocumentManager manager = new TeXDocumentManager(System.out);
        TeXEventWriter out = manager.getEventWriter();
        TeXTableObject table = new TeXTableObject(3, 3).row(0, "t", "t", "t")
                .row(1, "a", "a", "a").row(2, "b", "b", "b");
        table.getHorizontalLinesMatrix().fillFirstRow(true).fillLastRow(false).fillRectangle(1, 1, 2, 2, true);
        table.write(out);
    }

}
