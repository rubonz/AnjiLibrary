package com.darkempire.anjifx.geometry;

import com.darkempire.anji.annotation.AnjiUtil;
import javafx.scene.shape.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class ShapeUtil {
    private ShapeUtil() {
    }

    public static Path createRoundedRectagnle(double x, double y, double width, double height, double a) {
        Path result = new Path();
        double xend = x + width;
        double yend = y + height;
        result.getElements().addAll(new MoveTo(x, y + a), new ArcTo(a, a, 90, x + a, y, false, true), new LineTo(xend - a, y),
                new ArcTo(a, a, 90, xend, y + a, false, true), new LineTo(xend, yend - a), new ArcTo(a, a, 90, xend - a, yend, false, true),
                new LineTo(x + a, yend), new ArcTo(a, a, 90, x, yend - a, false, true), new LineTo(x, y + a));
        return result;
    }
}
