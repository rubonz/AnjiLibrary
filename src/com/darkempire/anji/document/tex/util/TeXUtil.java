package com.darkempire.anji.document.tex.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.log.Log;

import java.util.List;

/**
 * Created by siredvin on 05.10.14.
 */
@AnjiUtil
public final class TeXUtil {
    private TeXUtil() {
    }

    /**
     * Записує об’єкти, додаючи останній через and.
     * Наприклад, {1,2,3} -> 1,2 та 3
     *
     * @param list список об’єктів
     * @param out  джерело, куди записувати
     */
    public static void writeWithAnd(List<? extends ITeXObject> list, TeXEventWriter out) {
        int size = list.size();
        int ddsize = size - 2;
        int dsize = size - 1;
        for (int i = 0; i < size; i++) {
            Log.log(Log.debugIndex, i, ddsize);
            out.write(list.get(i));
            if (i == ddsize) {
                out.append("\\text{ та }");
                continue;
            }
            if (i == dsize || i == 0) {
                continue;
            }
            out.append(',');
        }
    }
}
