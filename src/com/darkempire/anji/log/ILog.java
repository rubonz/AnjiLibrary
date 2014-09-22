package com.darkempire.anji.log;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

/**
 * Created by siredvin on 05.09.14.
 */
public interface ILog {
    //region Керування каналами
    public int addString(String channel);

    public void addStream(PrintStream stream);

    public void addErrorStream(PrintStream stream);

    public void removeSteam(PrintStream stream);

    public void removeErrorStream(PrintStream stream);

    public void setAllow(int index, boolean value);

    public void setHidePrefix(int index, boolean value);
    //endregion

    //region Логування
    public void log(int index, String x);

    public void log(int index, Object x);

    public void log(int index, int x);

    public void log(int index, long x);

    public void log(int index, byte x);

    public void log(int index, char x);

    public void log(int index, double x);

    public void log(int index, float x);

    public void log(int index, boolean x);

    public void log(int index, Object... x);

    public void log(int index, String... x);

    public void log(int index, int[] x);

    public void log(int index, long[] x);

    public void log(int index, byte[] x);

    public void log(int index, char[] x);

    public void log(int index, double[] x);

    public void log(int index, float[] x);

    public void log(int index, boolean[] x);

    public void logf(int index, String format, Object... x);
    //endregion

    //region Виведення помилок
    public void err(int index, String text);

    public void err(int index, Object o);

    public void err(int index, int x);

    public void err(int index, long x);

    public void err(int index, byte x);

    public void err(int index, char x);

    public void err(int index, double x);

    public void err(int index, float x);

    public void err(int index, boolean x);

    public void err(int index, Object... objects);

    public void err(int index, String... x);

    public void err(int index, int[] x);

    public void err(int index, long[] x);

    public void err(int index, byte[] x);

    public void err(int index, char[] x);

    public void err(int index, double[] x);

    public void err(int index, float[] x);

    public void err(int index, boolean[] x);

    public void errf(int index, String format, Object... objects);
    //endregion

    //region Спеціальна частина
    public void error(int index, Throwable e);
    //endregion

    //region Налаштування
    public void setShowTime(int index, boolean value);

    public void setDateTimeFormat(int index, DateTimeFormatter format);

    public void isNewLineAfterPrefix(int index, boolean value);
    //endregion

    //region Методи очищення
    public void anji_clear();
    //endregion

    //region Автоматичне генерування файлів-логів
    public boolean isSupportAutoLog();

    public void appendNewTimeDateLog();

    public void appendDefaultLog();
    //endregion

    //region Видалення та додавання конкретних каналів
    public void removeConsoleLog();

    public void removeConsoleError();

    public void addConsoleLog();

    public void addConsoleError();
    //endregion
}
