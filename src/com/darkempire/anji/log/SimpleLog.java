package com.darkempire.anji.log;

import com.darkempire.internal.anji.AnjiInternal;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by siredvin on 05.09.14.
 */
public class SimpleLog implements ILog {
    private List<String> configList = new ArrayList<>();
    private List<PrintStream> out = new LinkedList<>();
    private List<PrintStream> err = new LinkedList<>();
    private List<DateTimeFormatter> logDateTimeFormat;
    private BitSet allowSet = new BitSet();
    private BitSet hidePrefix = new BitSet();
    private BitSet showTime = new BitSet();
    private BitSet newLineAfterPrefix = new BitSet();

    public SimpleLog() {
        out.add(System.out);
        err.add(System.err);
        logDateTimeFormat = new ArrayList<>();
    }

    //region Керування каналами
    @Override
    public int addString(String channel) {
        int result = configList.indexOf(channel);
        if (result == -1)
            result = configList.size();
        configList.add(channel);
        allowSet.set(result, true);
        showTime.set(result, true);
        newLineAfterPrefix.set(result, true);
        logDateTimeFormat.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return result;
    }

    @Override
    public void addStream(PrintStream stream) {
        out.add(stream);
    }

    @Override
    public void addErrorStream(PrintStream stream) {
        err.add(stream);
    }

    @Override
    public void removeSteam(PrintStream stream) {
        if (out.remove(stream))
            stream.close();
    }

    @Override
    public void removeErrorStream(PrintStream stream) {
        if (err.remove(stream))
            stream.close();
    }

    @Override
    public void setAllow(int index, boolean value) {
        allowSet.set(index, value);
    }

    @Override
    public void setHidePrefix(int index, boolean value) {
        hidePrefix.set(index, value);
    }
    //endregion

    //region Логування
    @Override
    public void log(int index, String x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, Object x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, int x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, long x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, byte x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, char x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, double x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, float x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, boolean x) {
        impl_print(index, x, out);
    }

    @Override
    public void log(int index, Object... x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, String... x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, int[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, long[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, byte[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, char[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, double[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, float[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void log(int index, boolean[] x) {
        impl_prints(index, out, x);
    }

    @Override
    public void logf(int index, String format, Object... x) {
        impl_printf(index, format, out, x);
    }
    //endregion

    //region Виведення помилок
    @Override
    public void err(int index, String text) {
        impl_print(index, text, err);
    }

    @Override
    public void err(int index, Object o) {
        impl_print(index, o, err);
    }

    @Override
    public void err(int index, int x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, long x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, byte x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, char x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, double x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, float x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, boolean x) {
        impl_print(index, x, out);
    }

    @Override
    public void err(int index, Object... objects) {
        impl_prints(index, err, objects);
    }

    @Override
    public void err(int index, String... x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, int[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, long[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, byte[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, char[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, double[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, float[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void err(int index, boolean[] x) {
        impl_prints(index, err, x);
    }

    @Override
    public void errf(int index, String format, Object... objects) {
        impl_printf(index, format, err, objects);
    }
    //endregion

    //region Спеціальна частина
    @Override
    public void error(int index, Throwable e) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : err) {
                impl_prefix(name, stream);
                e.printStackTrace(stream);
            }
        }
    }
    //endregion

    //region Внутрішня частника (print)
    private void impl_print(int index, String text, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(text);
            }
        }
    }

    private void impl_print(int index, Object o, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(o);
            }
        }
    }

    private void impl_print(int index, int x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, long x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, byte x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, char x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, float x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, double x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    private void impl_print(int index, boolean x, Iterable<PrintStream> streamIterable) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }
    //endregion

    //region Внутрішня частина (prints)
    private void impl_prints(int index, Iterable<PrintStream> streamIterable, String... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (String o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, Object... objects) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (Object o : objects) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, int... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (int o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, long... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (long o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, byte... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (byte o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, float... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (float o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, char... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (float o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, double... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (double o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    private void impl_prints(int index, Iterable<PrintStream> streamIterable, boolean... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                for (boolean o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }
    //endregion

    //region Внутрішня частина (printf та префікс)
    private void impl_printf(int index, String format, Iterable<PrintStream> streamIterable, Object... objects) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamIterable) {
                impl_prefix(name, stream);
                stream.printf(format, objects);
            }
        }
    }

    private void impl_prefix(String name, PrintStream stream) {
        int index = configList.indexOf(name);
        if (!hidePrefix.get(index)) {
            stream.print('[');
            if (showTime.get(index)) {
                stream.print(LocalDateTime.now().format(logDateTimeFormat.get(index)).replace('T', ' '));
                stream.print(":");
            }
            stream.print(name);
            stream.print("]:");
            if (newLineAfterPrefix.get(index)) {
                stream.println();
            }
        }
    }
    //endregion

    //region Налаштування
    @Override
    public void setShowTime(int index, boolean value) {
        showTime.set(index, value);
    }

    @Override
    public void setDateTimeFormat(int index, DateTimeFormatter format) {
        logDateTimeFormat.set(index, format);
    }

    @Override
    public void isNewLineAfterPrefix(int index, boolean value) {
        newLineAfterPrefix.set(index, value);
    }
    //endregion

    //region Методи очищення
    @Override
    @Deprecated
    @AnjiInternal
    public void anji_clear() {
        out.forEach(PrintStream::close);
        err.forEach(PrintStream::close);
        allowSet.clear();
        configList.clear();
    }
    //endregion

    //region Автоматичне генерування файлів-логів
    @Override
    public boolean isSupportAutoLog() {
        return false;
    }

    @Override
    public void appendNewTimeDateLog() {

    }

    @Override
    public void appendDefaultLog() {

    }
    //endregion

    //region Видалення та додавання конкретних каналів
    @Override
    public void removeConsoleLog() {
        if (out.contains(System.out))
            out.remove(System.out);
    }

    @Override
    public void removeConsoleError() {
        if (err.contains(System.err))
            err.remove(System.err);
    }

    @Override
    public void addConsoleLog() {
        if (!out.contains(System.out))
            out.add(System.out);
    }

    @Override
    public void addConsoleError() {
        if (!err.contains(System.err))
            err.add(System.err);
    }
    //endregion
}

