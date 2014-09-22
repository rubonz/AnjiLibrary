package com.darkempire.anji.util;

import com.darkempire.anji.annotation.AnjiUtil;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 25.11.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class Util {
    //private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private Util() {
    }

    public static String clearBuilder(StringBuilder stringBuilder) {
        String result = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return result;
    }

    public static StringBuilder removeLastChar(StringBuilder stringBuilder) {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder;
    }

    public static StringBuilder removeLastChars(StringBuilder stringBuilder, int count) {
        for (int i = 0; i < count; i++) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder;
    }

    public static <T> Collection<T> intersection(Collection<T> s1, Collection<T> s2) {
        return s1.stream().filter(s2::contains).collect(Collectors.toList());
    }
    /*public static Double parseLocalDouble(String s){
        return numberFormat.parse(s).doubleValue();
    }   */
}
