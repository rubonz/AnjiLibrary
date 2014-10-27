package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;

/**
 * Created by siredvin on 27.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class FormatUtils {
    private FormatUtils() {
    }

    /**
     * Додає число до тексту.
     * Якщо число меньше нуля, то просто його додає.
     * Якщо число більше нуля, то перед ним буде стояти +
     * Якщо число нуль, то нічого не відбудиться
     *
     * @param value   число
     * @param builder текст
     * @return змінений текст
     */
    public static StringBuilder appendNumber(double value, StringBuilder builder) {
        if (value != 0) {
            if (value > 0) {
                builder.append('+');
            }
            builder.append(MathMachine.numberFormat.format(value));
        }
        return builder;
    }
}
