package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;

@AnjiUtil
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * Простий метод для перемішування символьного рядка
     *
     * @param s     символьний рядок
     * @param count кількість перемішувань
     * @return перемішаний символьний рядок
     */
    public static String simpleSuffle(String s, int count) {
        char[] temp = s.toCharArray();
        for (int i = 0; i < count; i++) {
            int i1 = (int) (Math.random() * (temp.length - 1));
            int i2 = (int) (Math.random() * (temp.length - 1));
            char k = temp[i1];
            temp[i1] = temp[i2];
            temp[i2] = k;
        }
        StringBuilder temp1 = new StringBuilder();
        for (char k : temp) {
            temp1.append(k);
        }
        return temp1.toString();
    }

    /**
     * Повертає рядок з пробілами
     *
     * @param len кількість пробілів
     * @return символьний рядок пробілів
     */
    public static String spaceString(int len) {
        StringBuilder c = new StringBuilder();
        while (len > 0) {
            c.append(" ");
            len--;
        }
        return c.toString();
    }
}
