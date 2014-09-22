package com.darkempire.anji.log;

import com.darkempire.internal.anji.AnjiConsts;
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
    //region Автоматичне генерування файлів-логів
    @Override
    public boolean isSupportAutoLog() {
        return true;
    }

    @Override
    public void appendNewTimeDateLog() {
        String fileName = "anji-log-" + (LocalDateTime.now()).toString() + ".txt";
        String errFileName = "anji-err-" + (LocalDateTime.now()).toString() + ".txt";
        File dir = AnjiConsts.logDir;
        if (!Files.exists(dir.toPath())) {
            if (!dir.mkdir()) {
                return;
            }
        }
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
    }

    @Override
    public void appendDefaultLog() {
        String fileName = "anji-log.txt";
        String errFileName = "anji-err.txt";
        File dir = AnjiConsts.logDir;
        if (!Files.exists(dir.toPath())) {
            if (!dir.mkdir()) {
                return;
            }
        }
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
    }

    //endregion
}
