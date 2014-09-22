package com.darkempire.anjifx.beans;

import com.darkempire.anji.annotation.AnjiUtil;

import java.util.HashMap;

/**
 * Create in 13:46
 * Created by siredvin on 09.04.14.
 */
@AnjiUtil
public final class AnjiBeanUtil {
    private static HashMap<String, String> typeForPropertyMap;

    static {
        typeForPropertyMap = new HashMap<>();
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiDoubleVectorProperty", " com.darkempire.math.struct.vector.DoubleVector");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiFuzzyBoolProperty", " com.darkempire.math.struct.logic.FuzzyBool");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiLongProperty", "long");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiMapProperty", " java.util.Map");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiPoint3DProperty", " com.darkempire.math.struct.geometry.geomerty3d.Point3D");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiBooleanProperty", "boolean");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiBoundedLongProperty", "long");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiColorProperty", " javafx.scene.paint.Color");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiObjectProperty", "Object");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiSQLDateProperty", " java.sql.Date");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiFixedPointProperty", " com.darkempire.math.struct.geometry.geometrynd.FixedPoint");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiChooseStringProperty", "String");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiIntegerProperty", "int");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiDoubleProperty", "double");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiBigFractionProperty", " com.darkempire.math.struct.number.BigFraction");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiBoundedDoubleProperty", "double");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiPoint2DProperty", " com.darkempire.math.struct.geometry.geomerty2d.Point2D");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiEnumProperty", "Object");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiLongFractionProperty", " com.darkempire.math.struct.number.LongFraction");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiListProperty", " java.util.List");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiVector2DProperty", " com.darkempire.math.struct.geometry.geomerty2d.Vector2D");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiSetProperty", " java.util.Set");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiComplexProperty", " com.darkempire.math.struct.complex.Complex");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiFloatProperty", "float");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiFractionProperty", " com.darkempire.math.struct.number.Fraction");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiDateProperty", " java.time.LocalDate");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiVector3DProperty", " com.darkempire.math.struct.geometry.geomerty3d.Vector3D");
        typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiStringProperty", "String");
    }

    private AnjiBeanUtil() {
    }

    public static String getTypeFromProperty(String beanName) {
        return typeForPropertyMap.get(beanName);
    }
}
