package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.*;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.vector.NumberVector;

import java.util.List;

/**
 * Created by siredvin on 25.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class ListUtil {
    private ListUtil() {
    }

    public static <T> List<T> reverse(List<T> v) {
        int size = v.size();
        int asize = size / 2;
        for (int i = 0; i < asize; i++) {
            int rSize = size - i - 1;
            T temp = v.get(i);
            v.set(i, v.get(rSize));
            v.set(rSize, temp);
        }
        return v;
    }

    public static <T> List<T> reverse(List<T> v, int startMargin) {
        return reverse(v.subList(startMargin, v.size()));
    }

    public static <T> List<T> reverse(List<T> v, int startMargin, int endMargin) {
        return reverse(v.subList(startMargin, endMargin));
    }
}
