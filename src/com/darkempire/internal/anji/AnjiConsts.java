package com.darkempire.internal.anji;

import java.io.File;

/**
 * Created by siredvin on 13.09.14.
 */
@com.darkempire.anji.annotation.AnjiConsts
public final class AnjiConsts {
    private AnjiConsts() {
    }

    @AnjiInternal
    public static final File logDir = new File("./logs");
}
