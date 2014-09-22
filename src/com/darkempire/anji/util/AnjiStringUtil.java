package com.darkempire.anji.util;

import com.darkempire.anji.annotation.AnjiUtil;

/**
 * Create in 12:55
 * Created by siredvin on 08.04.14.
 */
@AnjiUtil
public final class AnjiStringUtil {
    private AnjiStringUtil() {
    }

    /**
     * Функція переводить рядок с формату aAaAAa_BbbBBb -&gt; AaaaaaaBbbbbb
     *
     * @param string рядок, для переводу
     * @return переведений рядок
     */
    public static String fromSlashSplitToCAMEL(String string) {
        String[] s = string.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String ss : s) {
            stringBuilder.append(ss.substring(0, 1).toUpperCase());
            stringBuilder.append(ss.substring(1).toLowerCase());
        }
        return stringBuilder.toString();
    }

    public static String generateCharString(char c, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(c);
        }
        return builder.toString();
    }
}
