package com.darkempire.anjifx.beans.property.transform;

import com.darkempire.anji.annotation.AnjiUtil;
import javafx.beans.property.Property;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class Transform {
    private Transform() {
    }

    public static ControlTransformProperty<Long, Integer> transformLongToInt(Property<Long> property) {
        return new ControlTransformProperty<>(property, new LongIntTransform());
    }

    public static ControlTransformProperty<Double, Float> transformDoubleToFloat(Property<Double> property) {
        return new ControlTransformProperty<>(property, new DoubleFloatTransform());
    }

    public static ControlTransformProperty<Double, Long> transformDoubleToLong(Property<Double> property) {
        return new ControlTransformProperty<>(property, new DoubleLongTransform());
    }

    public static ControlTransformProperty<Number, Long> transformNumberToLong(Property<Number> property) {
        return new ControlTransformProperty<>(property, new NumberToLongTransform());
    }

    public static ControlTransformProperty<Number, Long> controlTransformNumberToLong(Property<Number> property) {
        return new ControlTransformProperty<>(property, new NumberToLongTransform());
    }

    public static ControlTransformProperty<LocalDate, Date> transformDateToSQLDate(Property<LocalDate> property) {
        return new ControlTransformProperty<>(property, new DateToSQLDateTransform());
    }


}
