package com.darkempire.anjifx.dialog;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.anjifx.beans.property.AnjiChooseStringProperty;
import com.darkempire.anjifx.beans.property.AnjiColorProperty;
import com.darkempire.anjifx.beans.property.AnjiStringProperty;
import com.darkempire.anjifx.dialog.property.PropertyEditDialog;
import com.darkempire.internal.anji.LocalHolder;
import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 14.11.13
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class DialogUtil {
    private DialogUtil() {
    }

    public static PropertyEditDialog createDialog(AbstractAnjiProperty... values) {
        return new PropertyEditDialog(LocalHolder.anji_resourceBundle.getString("anjifx.dialog.property.content"), values);
    }

    public static PropertyEditDialog createDialog(Collection<AbstractAnjiProperty> values) {
        return new PropertyEditDialog(LocalHolder.anji_resourceBundle.getString("anjifx.dialog.property.content"), values);
    }

    public static PropertyEditDialog createDialog(String title, AbstractAnjiProperty... values) {
        return new PropertyEditDialog(title, values);
    }

    public static PropertyEditDialog createDialog(String title, Collection<AbstractAnjiProperty> values) {
        return new PropertyEditDialog(title, values);
    }

    public static String createStringDialog(String name) {
        return createStringDialog(name, LocalHolder.anji_resourceBundle.getString("anjifx.dialog.property.content"), "");
    }

    public static String createStringDialog(String name, String title) {
        return createStringDialog(name, title, "");
    }

    public static String createStringDialog(String name, String title, String def) {
        AnjiStringProperty stringProperty = new AnjiStringProperty(name, def);
        PropertyEditDialog propertyEditDialog = new PropertyEditDialog(title, stringProperty);
        propertyEditDialog.show();
        return stringProperty.getValue();
    }

    public static String selectDialog(String name, Collection<String> values) {
        return selectDialog(name, values, LocalHolder.anji_resourceBundle.getString("anjifx.dialog.property.content"), values.iterator().next());
    }

    public static String selectDialog(String name, Collection<String> values, String title) {
        return selectDialog(name, values, title, values.iterator().next());
    }

    public static String selectDialog(String name, Collection<String> values, String title, String def) {
        AnjiChooseStringProperty stringProperty = new AnjiChooseStringProperty(name, values);
        stringProperty.setValue(def);
        PropertyEditDialog propertyEditDialog = new PropertyEditDialog(title, stringProperty);
        propertyEditDialog.show();
        return stringProperty.getValue();
    }

    public static Color colorDialog(String name, String title, Color defColor) {
        AnjiColorProperty colorProperty = new AnjiColorProperty(name, defColor);
        PropertyEditDialog propertyEditDialog = new PropertyEditDialog(title, colorProperty);
        propertyEditDialog.show();
        return colorProperty.getValue();
    }

    public static Color colorDialog(String name, String title) {
        return colorDialog(name, title, Color.BLACK);
    }

    public static Color colorDialog(String name) {
        return colorDialog(name, LocalHolder.anji_resourceBundle.getString("anjifx.dialog.property.content"), Color.BLACK);
    }

}