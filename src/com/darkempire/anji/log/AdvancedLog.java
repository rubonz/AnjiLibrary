package com.darkempire.anji.log;

import com.darkempire.internal.AnjiConsts;
import com.darkempire.internal.anji.LocalHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.LocalDateTime;

/**
 * Created by siredvin on 13.09.14.
 */
public class AdvancedLog extends SimpleLog {
    protected boolean isDefaultLogCreated;
    protected boolean isTimeLogCreated;

    public AdvancedLog() {
        super();
        isDefaultLogCreated = isTimeLogCreated = false;
    }

    //region Автоматичне генерування файлів-логів
    @Override
    public void appendNewTimeDateLog() {
        if (isTimeLogCreated)
            return;
        String fileName = "anji-log-" + (LocalDateTime.now()).toString() + ".txt";
        String errFileName = "anji-err-" + (LocalDateTime.now()).toString() + ".txt";
        File dir = AnjiConsts.logDir;
        AnjiConsts.checkDir(dir);
        File file = new File(dir, fileName);
        File errFile = new File(dir, errFileName);
        try {
            PrintStream logStream = new PrintStream(new FileOutputStream(file));
            PrintStream errStream = new PrintStream(new FileOutputStream(errFile));
            addStream(logStream);
            addErrorStream(errStream);
        } catch (FileNotFoundException e) {
            Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.createLogError"));
            Log.error(Log.coreIndex, e);
        }
        isTimeLogCreated = true;
    }

    @Override
    public void appendDefaultLog() {
        if (isDefaultLogCreated)
            return;
        String fileName = "anji-log.txt";
        String errFileName = "anji-err.txt";
        File dir = AnjiConsts.logDir;
        AnjiConsts.checkDir(dir);
        File file = new File(dir, fileName);
        File errFile = new File(dir, errFileName);
        try {
            PrintStream logStream = new PrintStream(new FileOutputStream(file));
            PrintStream errStream = new PrintStream(new FileOutputStream(errFile));
            addStream(logStream);
            addErrorStream(errStream);
        } catch (FileNotFoundException e) {
            Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.createLogError"));
            Log.error(Log.coreIndex, e);
        }
        isDefaultLogCreated = true;
    }
    //endregion

    //region Керування видимістю
    @Override
    public void allowConsoleOut(int index, int channel_index, boolean value) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
    }

    @Override
    public void allowConsoleErr(int index, int channel_index, boolean value) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
    }

    @Override
    public int getOutIndex(PrintStream stream) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getErrIndex(PrintStream stream) {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getConsoleOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getConsoleErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getDefaultLogOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getDefaultLogErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getTimeLogOutIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }

    @Override
    public int getTimeLogErrIndex() {
        Log.err(Log.coreIndex, LocalHolder.anji_resourceBundle.getString("anji.log.advancedlog.channelvisible"));
        return -1;
    }
    //endregion
}
