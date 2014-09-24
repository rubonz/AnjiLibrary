package com.darkempire.anji.log;

import com.darkempire.internal.anji.LocalHolder;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by siredvin on 24.09.14.
 */
public class ChannelControlLog extends AdvancedLog {
    protected int defaultLogIndex;
    protected int defaultErrIndex;
    protected int timeLogIndex;
    protected int timeErrIndex;
    protected List<BitSet> hideChannelOutList;
    protected List<BitSet> hideChannelErrList;

    public ChannelControlLog() {
        super();
        defaultLogIndex = defaultErrIndex = timeErrIndex = timeLogIndex = -1;
        hideChannelOutList = new ArrayList<>();
        hideChannelErrList = new ArrayList<>();
    }

    //region Керування каналами
    @Override
    public int addString(String channel) {
        hideChannelOutList.add(new BitSet());
        hideChannelErrList.add(new BitSet());
        return super.addString(channel);
    }
    //endregion

    //region Автоматичне генерування файлів-логів
    @Override
    public void appendNewTimeDateLog() {
        if (!isTimeLogCreated) {
            timeLogIndex = out.size();
            timeLogIndex = err.size();
            super.appendNewTimeDateLog();
        }
    }

    @Override
    public void appendDefaultLog() {
        if (!isDefaultLogCreated) {
            defaultLogIndex = out.size();
            defaultLogIndex = err.size();
            super.appendDefaultLog();
        }
    }
    //endregion

    //region Керування видимістю
    @Override
    public void allowConsoleOut(int index, int channel_index, boolean value) {
        hideChannelOutList.get(index).set(channel_index, !value);
    }

    @Override
    public void allowConsoleErr(int index, int channel_index, boolean value) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.simplelog.channelvisible"));
    }

    @Override
    public int getOutIndex(PrintStream stream) {
        return out.indexOf(stream);
    }

    @Override
    public int getErrIndex(PrintStream stream) {
        return err.indexOf(stream);
    }

    @Override
    public int getConsoleOutIndex() {
        return out.indexOf(System.out);
    }

    @Override
    public int getConsoleErrIndex() {
        return err.indexOf(System.err);
    }

    @Override
    public int getDefaultLogOutIndex() {
        return defaultLogIndex;
    }

    @Override
    public int getDefaultLogErrIndex() {
        return defaultErrIndex;
    }

    @Override
    public int getTimeLogOutIndex() {
        return timeLogIndex;
    }

    @Override
    public int getTimeLogErrIndex() {
        return timeErrIndex;
    }
    //endregion

    //region Внутрішня частника (print)
    protected void impl_print(int index, String text, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(text);
                }
            }
        }
    }

    protected void impl_print(int index, Object o, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(o);
                }
            }
        }
    }

    protected void impl_print(int index, int x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, long x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, byte x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, char x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, float x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, double x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }

    protected void impl_print(int index, boolean x, List<PrintStream> streamList) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.println(x);
                }
            }
        }
    }
    //endregion

    //region Внутрішня частина (prints)
    protected void impl_prints(int index, List<PrintStream> streamList, String... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (String o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, Object... objects) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (Object o : objects) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, int... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (int o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, long... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (long o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, byte... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (byte o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, float... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (float o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, char... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (char o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, double... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (double o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }

    protected void impl_prints(int index, List<PrintStream> streamList, boolean... x) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    for (boolean o : x) {
                        stream.print(o);
                        stream.print(' ');
                    }
                    stream.println();
                }
            }
        }
    }
    //endregion

    //region Внутрішня частина (printf та префікс)
    protected void impl_printf(int index, String format, List<PrintStream> streamList, Object... objects) {
        if (allowSet.get(index)) {
            BitSet hideChannel = streamList == out ? hideChannelOutList.get(index) : hideChannelErrList.get(index);
            String name = configList.get(index);
            int size = streamList.size();
            for (int i = 0; i < size; i++) {
                PrintStream stream = streamList.get(i);
                if (!hideChannel.get(i)) {
                    impl_prefix(name, stream);
                    stream.printf(format, objects);
                }
            }
        }
    }
    //endregion
}
