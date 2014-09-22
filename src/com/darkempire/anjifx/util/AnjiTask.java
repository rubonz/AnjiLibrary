package com.darkempire.anjifx.util;

import javafx.concurrent.Task;

/**
 * Create in 15:37
 * Created by siredvin on 09.12.13.
 */
public abstract class AnjiTask<V> extends Task<V> {
    protected void updateProgress(double v) {
        updateProgress(v, 1);
    }
}
