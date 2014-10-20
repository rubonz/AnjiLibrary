package com.darkempire.anji.util;

import com.darkempire.anji.annotation.AnjiUtil;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class ClassUtil {
    private ClassUtil() {
    }

    /**
     * Перевіряє, чи є заданий клас суперкласом для класу, що перевіряється
     *
     * @param superClass суперклас
     * @param checked    клас, що перевіряється
     * @return чи суперкласс
     */
    public static boolean isSuperClass(Class superClass, Class checked) {
        if (superClass == checked)
            return true;
        while (checked != Object.class) {
            checked = checked.getSuperclass();
            if (checked == superClass) {
                return true;
            }
        }
        return false;
    }
}
