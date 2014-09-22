package com.darkempire.anji.util;

import com.darkempire.anji.annotation.AnjiUtil;

/**
 * Create in 11:06
 * Created by siredvin on 19.05.14.
 */
@AnjiUtil
public final class BitsUtil {
    private static final int g31 = 0b0100_1001_0010_0100_1001_0010_0100_1001;
    private static final int g32 = 0b0011_1000_0001_1100_0000_1110_0000_0111;

    private BitsUtil() {
    }

    /**
     * @param v число
     * @return кількість ненульових біт у числі
     */
    public static int getBitCount(int v) {
        v = (v & g31) + ((v >> 1) & g31) + ((v >> 2) & g31);
        v = ((v + (v >> 3)) & g32) + ((v >> 6) & g32);
        return (v + (v >> 9) + (v >> 18) + (v >> 27)) & 0x3f;
    }

}
