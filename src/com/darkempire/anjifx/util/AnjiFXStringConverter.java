package com.darkempire.anjifx.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.math.struct.complex.Complex;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;
import com.darkempire.math.struct.geometry.geomerty3d.Point3D;
import com.darkempire.math.struct.geometry.geomerty3d.Vector3D;
import com.darkempire.math.struct.logic.FuzzyBool;
import com.darkempire.math.struct.number.BigFraction;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.number.LongFraction;
import javafx.scene.paint.Color;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 08.11.13
 * Time: 9:54
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class AnjiFXStringConverter {
    private AnjiFXStringConverter() {
    }

    public static String colorToRGBString(Color c) {
        return c.toString().replace("0x", "#");
    }

    @SuppressWarnings("unchecked")
    public static void setValue(AbstractAnjiProperty property, String value) {
        switch (property.getType()) {
            case STRING_TYPE:
                property.setValue(value);
                break;
            case BOOLEAN_TYPE:
                property.setValue(Boolean.valueOf(value));
                break;
            case DOUBLE_TYPE:
                property.setValue(Double.valueOf(value));
                break;
            case INT_TYPE:
                property.setValue(Integer.valueOf(value));
                break;
            case CHOOSE_TYPE:
                property.setValue(value);
                break;
            case POINT2D_TYPE:
                property.setValue(Point2D.valueOf(value));
                break;
            case VECTOR2D_TYPE:
                property.setValue(Vector2D.valueOf(value));
                break;
            case COLOR_TYPE:
                property.setValue(Color.valueOf(value));
                break;
            case LINEARGRADIEN_TYPE:
                break;
            case RADIALGRADIENT_TYPE:
                break;
            case FRACTION_TYPE:
                property.setValue(Fraction.valueOf(value));
                break;
            case LONGFRACTION_TYPE:
                property.setValue(LongFraction.valueOf(value));
                break;
            case BIGFRACTION_TYPE:
                property.setValue(BigFraction.valueOf(value));
                break;
            case FUZZYBOOL_TYPE:
                property.setValue(FuzzyBool.parseFuzzyBool(value));
                break;
            case LONG_TYPE:
                property.setValue(Long.valueOf(value));
                break;
            case FLOAT_TYPE:
                property.setValue(Float.valueOf(value));
                break;
            case POINT3D_TYPE:
                property.setValue(Point3D.valueOf(value));
                break;
            case VECTOR3D_TYPE:
                property.setValue(Vector3D.valueOf(value));
                break;
            case COMPLEX_TYPE:
                property.setValue(Complex.valueOf(value));
                break;
            case LONG_BOUNDED_TYPE:
                property.setValue(Long.valueOf(value));
                break;
            case DOUBLE_BOUNDED_TYPE:
                property.setValue(Double.valueOf(value));
                break;
            case SQLDATE_TYPE:
                property.setValue(Date.valueOf(value));
                break;
            case DATE_TYPE:
                property.setValue(Date.valueOf(value));
            case LIST_TYPE:
            case MAP_TYPE:
            case TREE_TYPE:
            case OBJECT_TYPE:
            case FIXEDPOINT_TYPE:
            case SET_TYPE:
            case DOUBLEVECTOR_TYPE:
            case ENUM_TYPE:
                throw new UnsupportedOperationException();
        }
    }
}
