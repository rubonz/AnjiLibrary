package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;

/**
 * Created by siredvin on 09.07.14.
 */
@AnjiUtil
public final class FloatUtils {
    private FloatUtils() {
    }

    public static int getFloatExp(float value) {
        return (Float.floatToIntBits(value) >>> 23 & ((1 << 8) - 1)) - ((1 << 7) - 1);
    }
}
