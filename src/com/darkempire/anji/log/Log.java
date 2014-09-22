package com.darkempire.anji.log;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.internal.anji.AnjiInternal;

import javax.annotation.PreDestroy;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class Log {

    public static final int coreIndex = 0;
    public static final int debugIndex = 1;
    public static final int sqlIndex = 2;
    public static final int texIndex = 3;
    public static final int logIndex = 4;

    private static ILog log = null;

    static {
        initAdvancedLog();
        log.appendDefaultLog();
    }

    private Log() {
    }

    //region Керування каналами
    public static int addString(String channel) {
        return log.addString(channel);
    }

    public static void addStream(PrintStream stream) {
        log.addStream(stream);
    }

    public static void addErrorStream(PrintStream stream) {
        log.addErrorStream(stream);
    }

    public static void removeSteam(PrintStream stream) {
        log.removeSteam(stream);
    }

    public static void removeErrorStream(PrintStream stream) {
        log.removeErrorStream(stream);
    }

    public static void setAllow(int index, boolean value) {
        log.setAllow(index, value);
    }

    public static void setHidePrefix(int index, boolean value) {
        log.setHidePrefix(index, value);
    }
    //endregion

    //region Логування
    public static void log(int index, String x) {
        log.log(index, x);
    }

    public static void log(int index, Object x) {
        log.log(index, x);
    }

    public static void log(int index, int x) {
        log.log(index, x);
    }

    public static void log(int index, long x) {
        log.log(index, x);
    }

    public static void log(int index, byte x) {
        log.log(index, x);
    }

    public static void log(int index, char x) {
        log.log(index, x);
    }

    public static void log(int index, double x) {
        log.log(index, x);
    }

    public static void log(int index, float x) {
        log.log(index, x);
    }

    public static void log(int index, boolean x) {
        log.log(index, x);
    }

    public static void log(int index, Object... x) {
        log.log(index, x);
    }

    public static void log(int index, String... x) {
        log.log(index, x);
    }

    public static void log(int index, int[] x) {
        log.log(index, x);
    }

    public static void log(int index, long[] x) {
        log.log(index, x);
    }

    public static void log(int index, byte[] x) {
        log.log(index, x);
    }

    public static void log(int index, char[] x) {
        log.log(index, x);
    }

    public static void log(int index, double[] x) {
        log.log(index, x);
    }

    public static void log(int index, float[] x) {
        log.log(index, x);
    }

    public static void log(int index, boolean[] x) {
        log.log(index, x);
    }

    public static void logf(int index, String format, Object... x) {
        log.logf(index, format, x);
    }
    //endregion

    //region Виведення помилок
    public static void err(int index, String text) {
        log.err(index, text);
    }

    public static void err(int index, Object o) {
        log.err(index, o);
    }

    public static void err(int index, int x) {
        log.err(index, x);
    }

    public static void err(int index, long x) {
        log.err(index, x);
    }

    public static void err(int index, byte x) {
        log.err(index, x);
    }

    public static void err(int index, char x) {
        log.err(index, x);
    }

    public static void err(int index, double x) {
        log.err(index, x);
    }

    public static void err(int index, float x) {
        log.err(index, x);
    }

    public static void err(int index, boolean x) {
        log.err(index, x);
    }

    public static void err(int index, Object... objects) {
        log.err(index, objects);
    }

    public static void err(int index, String... x) {
        log.err(index, x);
    }

    public static void err(int index, int[] x) {
        log.err(index, x);
    }

    public static void err(int index, long[] x) {
        log.err(index, x);
    }

    public static void err(int index, byte[] x) {
        log.err(index, x);
    }

    public static void err(int index, char[] x) {
        log.err(index, x);
    }

    public static void err(int index, double[] x) {
        log.err(index, x);
    }

    public static void err(int index, float[] x) {
        log.err(index, x);
    }

    public static void err(int index, boolean[] x) {
        log.err(index, x);
    }

    public static void errf(int index, String format, Object... objects) {
        log.errf(index, format, objects);
    }
    //endregion

    //region Спеціальна частина
    public static void error(int index, Throwable e) {
        log.error(index, e);
    }
    //endregion

    //region Налаштування
    public static void setShowTime(int index, boolean value) {
        log.setShowTime(index, value);
    }

    public static void setDateTimeFormat(int index, DateTimeFormatter format) {
        log.setDateTimeFormat(index, format);
    }

    public static void isNewLineAfterPrefix(int index, boolean value) {
        log.isNewLineAfterPrefix(index, value);
    }
    //endregion

    //region Видалення та додавання конкретних каналів
    public static void removeConsoleLog() {
        log.removeConsoleLog();
    }

    public static void removeConsoleError() {
        log.removeConsoleError();
    }

    public static void addConsoleLog() {
        log.addConsoleLog();
    }

    public static void addConsoleError() {
        log.addConsoleError();
    }
    //endregion

    @Deprecated
    @AnjiInternal
    @PreDestroy
    public static void anji_clear() {
        if (log != null) {
            log.anji_clear();
        }
    }

    //region Ініціалізація логів
    public static void initSimpleLog() {
        if (log != null) {
            log.anji_clear();
        }
        log = new SimpleLog();
        initStartChannel();
    }

    public static void initAdvancedLog() {
        if (log != null) {
            log.anji_clear();
        }
        log = new AdvancedLog();
        initStartChannel();
    }

    private static void initStartChannel() {
        addString("Anji Core");//0
        addString("Anji Debug");//1
        addString("SQL");//2
        addString("Anji-TeX");//3
        addString("Log");//4
    }
    //endregion
}
