package sample;

import com.darkempire.math.struct.fractal.lsystem.CharType;
import com.darkempire.math.struct.fractal.lsystem.SimpleLSystem2D;
import com.darkempire.math.struct.fractal.lsystem.SimpleRule;

/**
 * Created by siredvin on 29.09.14.
 */
public class DT1 {
    public static void main(String[] args) {
        String axiom = "F[X]F++F[X]F++F[X]F++F[X]F";
        SimpleLSystem2D system2D = new SimpleLSystem2D(null, 0, axiom, null, null);
        system2D.setRule(new SimpleRule("F"), 'F')
                .setRule(new SimpleRule("x[X]x"), 'x')
                .setRule(new SimpleRule("[+x][-x][++x][--x]"), 'X')
                .setCharType(CharType.MOVE, 'F')
                .setCharType(CharType.MOVE, 'x')
                .setCharType(CharType.SKIP, 'X');
        System.out.println(system2D.createString(20));

    }
}
