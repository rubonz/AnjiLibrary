package com.darkempire.anji.document.tex.util;

import com.darkempire.anji.annotation.AnjiExperimental;
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

    private static final String simpleInit = "\\documentclass[paper=a4, fontsize=14pt]{article} % A4 paper and 11pt font size\n" +
            "\\usepackage{fullpage}\n" +
            "\\usepackage[T1]{fontenc} % Use 8-bit encoding that has 256 glyphs\n" +
            "\\usepackage{fourier} % Use the Adobe Utopia font for the document - comment this line to return to the LaTeX default\n" +
            "\\usepackage[english,ukrainian]{babel} % English language/hyphenation\n" +
            "\\usepackage{amsmath,amsfonts,amsthm} % Math packages\n" +
            "\\usepackage[unicode,colorlinks]{hyperref}" +
            "\\usepackage[utf8x]{inputenc}\n" +
            "\\usepackage{lipsum} % Used for inserting dummy 'Lorem ipsum' text into the template\n" +
            "\n" +
            "\\usepackage{sectsty} % Allows customizing section commands\n" +
            "\\allsectionsfont{\\centering \\normalfont\\scshape} % Make all sections centered, the default font and small caps\n" +
            "\\usepackage{geometry}\n" +
            "\\usepackage{graphicx}\n" +
            "\\usepackage{listings}\n" +
            "\\usepackage{makeidx}\n" +
            "\\usepackage{titling}\n" +
            "\\usepackage{abstract}\n" +
            "\\usepackage{color}\n" +
            "\\usepackage{fancyhdr} % Custom headers and footers\n" +
            "\\pagestyle{fancyplain} % Makes all pages in the document conform to the custom headers and footers\n" +
            "\\fancyhead{} % No page header - if you want one, create it in the same way as the footers below\n" +
            "\\fancyfoot[L]{} % Empty left footer\n" +
            "\\fancyfoot[C]{} % Empty center footer\n" +
            "\\fancyfoot[R]{\\thepage} % Page numbering for right footer\n" +
            "\\renewcommand{\\headrulewidth}{0pt} % Remove header underlines\n" +
            "\\renewcommand{\\footrulewidth}{0pt} % Remove footer underlines\n" +
            "\\setlength{\\headheight}{13.6pt} % Customize the height of the header";

    private static final String kpiTitle = "\\newgeometry{top=1cm}\n" +
            "\\newcommand{\\horrule}[1]{\\rule{\\linewidth}{#1}}\n" +
            "\\begin{titlepage}\n" +
            "\n" +
            "\\begin{center}\n" +
            "\\large Національний технічний університет \"Київський політехнічний інститут\"\\\\[4.5cm] \n" +
            "\\horrule{0.5pt} \\\\[0.4cm]\n" +
            "\\huge %s\\\\[0.6cm]\n" +
            "\\large %s\n" +
            "\\horrule{2pt} \\\\[3.7cm]\n" +
            "\\end{center}\n" +
            "\\begin{flushright}\n%s" +
            "\\end{flushright}\n" +
            "\n" +
            "\\vfill \n" +
            "\\begin{center}\n" +
            "{\\large %s} \n" +
            "{\\large \\LaTeX} \n" +
            "\\end{center}\n" +
            "\n" +
            "\\thispagestyle{empty}\n" +
            "\\end{titlepage}\n" +
            "\\restoregeometry";
    private TeXUtil() {
    }

    /**
     * Записує об’єкти, додаючи останній через and.
     * Наприклад, {1,2,3} -> 1,2 та 3
     *
     * @param list список об’єктів
     * @param out  джерело, куди записувати
     */
    public static <T extends ITeXObject> void writeWithAnd(TeXEventWriter out, List<T> list) {
        int size = list.size();
        int ddsize = size - 2;
        int dsize = size - 1;
        for (int i = 0; i < size; i++) {
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

    /**
     * Записує об’єкти, додаючи останній через and.
     * Наприклад, {1,2,3} -> 1,2 та 3
     *
     * @param list список об’єктів
     * @param out  джерело, куди записувати
     */
    public static <T extends ITeXObject> void writeWithAnd(TeXEventWriter out, T... list) {
        int size = list.length;
        int ddsize = size - 2;
        int dsize = size - 1;
        for (int i = 0; i < size; i++) {
            out.write(list[i]);
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

    /**
     * Записує об’єкти, додаючи останній через and.
     * Наприклад, {1,2,3} -> 1,2 та 3
     *
     * @param list список об’єктів
     * @param out  джерело, куди записувати
     */
    public static void writeWithAnd(TeXEventWriter out, String... list) {
        int size = list.length;
        int ddsize = size - 2;
        int dsize = size - 1;
        for (int i = 0; i < size; i++) {
            out.append(list[i]);
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

    public static TeXEventWriter initSimpleHeader(TeXEventWriter out) {
        return out.text(simpleInit);
    }

    @AnjiExperimental
    public static TeXEventWriter initKPITitle(TeXEventWriter out, String title, String subtitle, String author, String date) {
        return out.openEnvironment("document").text(String.format(kpiTitle, title, subtitle, author, date));
    }
}
