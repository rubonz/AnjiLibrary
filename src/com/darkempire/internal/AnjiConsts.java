package com.darkempire.internal;

import com.darkempire.internal.anji.AnjiInternal;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by siredvin on 13.09.14.
 */
@com.darkempire.anji.annotation.AnjiConsts
public final class AnjiConsts {
    private AnjiConsts() {
    }


    @AnjiInternal
    public static final File logDir = new File("./logs");
    @AnjiInternal
    public static final File imgDir = new File("./img");

    public static boolean checkDir(File dir) {
        return Files.exists(dir.toPath()) || dir.mkdir();
    }
}
