package com.darkempire.anji.log;

import com.darkempire.internal.anji.AnjiInternal;
import com.darkempire.internal.anji.LocalHolder;

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
    protected List<String> configList = new ArrayList<>();
    protected List<PrintStream> out = new LinkedList<>();
    protected List<PrintStream> err = new LinkedList<>();
    protected List<DateTimeFormatter> logDateTimeFormat;
    protected BitSet allowSet = new BitSet();
    protected BitSet hidePrefix = new BitSet();
    protected BitSet showTime = new BitSet();
    protected BitSet newLineAfterPrefix = new BitSet();

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
    protected void impl_print(int index, String text, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(text);
            }
        }
    }

    protected void impl_print(int index, Object o, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(o);
            }
        }
    }

    protected void impl_print(int index, int x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, long x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, byte x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, char x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, float x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, double x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }

    protected void impl_print(int index, boolean x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.println(x);
            }
        }
    }
    //endregion

    //region Внутрішня частина (prints)
    protected void impl_prints(int index, List<PrintStream> streamList, String... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (String o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, Object... objects) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (Object o : objects) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, int... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (int o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, long... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (long o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, byte... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (byte o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, float... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (float o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, char... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (float o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, double... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                for (double o : x) {
                    stream.print(o);
                    stream.print(' ');
                }
                stream.println();
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, boolean... x) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
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
    protected void impl_printf(int index, String format, List<PrintStream> streamList, Object... objects) {
        if (allowSet.get(index)) {
            String name = configList.get(index);
            for (PrintStream stream : streamList) {
                impl_prefix(name, stream);
                stream.printf(format, objects);
            }
        }
    }

    protected void impl_prefix(String name, PrintStream stream) {
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
    public void appendNewTimeDateLog() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.autolog"));
    }

    @Override
    public void appendDefaultLog() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.autolog"));
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

    //region Керування видимістю
    @Override
    public void allowConsoleOut(int index, int channel_index, boolean value) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
    }

    @Override
    public void allowConsoleErr(int index, int channel_index, boolean value) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
    }

    @Override
    public int getOutIndex(PrintStream stream) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getErrIndex(PrintStream stream) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getConsoleOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getConsoleErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getDefaultLogOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getDefaultLogErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getTimeLogOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }

    @Override
    public int getTimeLogErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
        return -1;
    }
    //endregion

}

